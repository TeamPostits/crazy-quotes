package se.awesome.data;

public class Quote {
	
	private String text;
	private String author;
	private int year;
	private String createdBy;
	
	public Quote(String text, String author, int year, String createdBy){
		this.text = text;
		this.author = author;
		this.year = year;
		this.createdBy = createdBy;
	}

	public String getText() {
		return text;
	}

	public String getAuthor() {
		return author;
	}

	public int getYear() {
		return year;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Quote){
			Quote q = (Quote) obj;
			return q.text.equals(this.text) && q.author.equals(this.author) && q.createdBy.equals(this.createdBy) && q.year == this.year;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return 37 * this.text.hashCode() + 37 * this.author.hashCode() + 37 * this.createdBy.hashCode();
	}
	
	

}
