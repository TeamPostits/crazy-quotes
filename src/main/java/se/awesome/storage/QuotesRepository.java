package se.awesome.storage;

import java.util.List;

import se.awesome.data.Quote;

public interface QuotesRepository {
	
	public boolean createQuote(Quote quote);
	
	public List<Quote> readQuotes();

}
