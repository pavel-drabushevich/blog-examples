package com.blogspot.pdrobushevich.screener.screener.data;

public class Company {

	private final String name;
	private final String symbol;
	private final double mktCap;
	private final double price;

	public Company(final String name, final String symbol, final double mktCap,
			final double price) {
		this.name = name;
		this.symbol = symbol;
		this.mktCap = mktCap;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public double getMktCap() {
		return mktCap;
	}

	public String getFormatedMktCap() {
		return mktCap + "B";
	}

	public double getPrice() {
		return price;
	}

}
