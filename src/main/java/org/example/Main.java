package org.example;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        loopThroughPokemon();
    }

    public static HtmlPage getPage(String url) {
        try (WebClient webClient = new WebClient()) { //Opens the web client only during this try catch block
            webClient.getOptions().setCssEnabled(false); //Disables CSS
            webClient.getOptions().setJavaScriptEnabled(false); //Disables JavaScript
            return webClient.getPage(url); //Returns the page at the url
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Error getting page"); //If the page isn't available for whatever reason, this will be printed
        return null; //And the method will return null
    }

    public static void loopThroughPokemon() {
        HtmlPage page = getPage("https://pokemondb.net/pokedex/all"); //Saves the page to be scraped as a variable

        List<HtmlElement> pokemonList = page.getByXPath("//table/tbody/tr/td/a"); //Gets all the rows with Pokémon data from the page
        for (int i = 0; i < pokemonList.size(); i++) {  //Loops through all the rows
            if (!pokemonList.get(i).getAttribute("title").contains("View Pokedex")) { //And removes all unnecessary entries
                pokemonList.remove(i);
                i--;
            }
        }

        int count = 0; //Counts the number of Pokémon
        for (int i=0; i<pokemonList.size(); i++) { //Loops through all the rows
            if (i == pokemonList.size() - 1) { //Prevents the last Pokémon from being skipped due to an IndexOutOfBoundsException
                count++; //Increments the count
                extractPokemon(pokemonList.get(i).getAttribute("href")); //Extracts the data from the scraped Pokémon
            }
            else if (!pokemonList.get(i).getAttribute("href").equals(pokemonList.get(i + 1).getAttribute("href"))) { //If the next Pokémon is a different Pokémon than the current
                    count++; //Increments the count
                    extractPokemon(pokemonList.get(i).getAttribute("href")); //Extracts the data from the scraped Pokémon
            }
        }
        System.out.println("Total: " + count); //As a control measure, the number of scraped Pokémon is printed
    }

    public static void extractPokemon(String url) {
        String baseUrl = "https://pokemondb.net";
        HtmlPage page = getPage(baseUrl+url); //Fetches the page for the Pokémon by combining the base and the individual part of the URL

        HtmlElement element = page.getFirstByXPath("//h1"); //Gets the Pokémon's name
        String name = element.asText(); //And saves it
        element = page.getFirstByXPath("//tr/td/strong"); //Gets the Pokémon's National Dex number
        String num = element.asText(); //And saves it

        System.out.println("#"+num + ": " + name); //Outputs the scraped data for validation purposes
    }
}