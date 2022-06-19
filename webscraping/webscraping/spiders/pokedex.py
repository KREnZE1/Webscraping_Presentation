import scrapy


class PokedexSpider(scrapy.Spider):
    name = 'pokedex'
    start_urls = ['https://pokemondb.net/pokedex/enamorus']

    def parse(self, response):
        name = response.xpath("//h1/text()").get()
        number = response.xpath("//tr/td/strong/text()").get()
        yield {
            'name': name,
            'number': number
        }
        url = response.xpath("//main/nav/a/@href").get()
        if url is not None:
            link = response.urljoin(url)
            yield scrapy.Request(link, callback=self.parse)
