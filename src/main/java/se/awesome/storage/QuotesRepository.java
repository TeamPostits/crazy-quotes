package se.awesome.storage;

import java.util.List;

import se.awesome.data.Quote;

public interface QuotesRepository {
	
	public List<Quote> readQuotes();
	
	public boolean createQuote(Quote quote);

}
