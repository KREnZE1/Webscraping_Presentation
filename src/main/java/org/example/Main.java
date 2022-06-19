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
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            return webClient.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Error getting page");
        return null;
    }

    public static void loopThroughPokemon() {
        HtmlPage page = getPage("https://pokemondb.net/pokedex/all");

        List<HtmlElement> pokemonList = page.getByXPath("//table/tbody/tr/td/a");
        for (int i = 0; i < pokemonList.size(); i++) {
            if (!pokemonList.get(i).getAttribute("title").contains("View Pokedex")) {
                pokemonList.remove(i);
                i--;
            }
        }

        int count = 0;
        for (int i=0; i<pokemonList.size(); i++) {
            //Fix isn't good, but prevents the IndexOutOfBoundsException
            if (i == pokemonList.size() - 1) {
                count++; // increment the count
                extractPokemon(pokemonList.get(i).getAttribute("href")); //Extract the data
            }
            else if (!pokemonList.get(i).getAttribute("href").equals(pokemonList.get(i + 1).getAttribute("href"))) {
                    count++; // increment the count
                    extractPokemon(pokemonList.get(i).getAttribute("href")); //Extract the data
            }
        }
        System.out.println("Total: " + count); //Count is correct, theoretically redundant
    }

    public static void extractPokemon(String url) {
        String baseUrl = "https://pokemondb.net";
        HtmlPage page = getPage(baseUrl+url);
        HtmlElement element = page.getFirstByXPath("//h1");
        String name = element.asText();
        element = page.getFirstByXPath("//tr/td/strong");
        String num = element.asText();

        System.out.println("#"+num + ": " + name);
    }
}