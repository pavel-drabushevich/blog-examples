package com.blogspot.pdrobushevich.screener.screener.data;

public class PriceCompanyFilter implements Filter<Company> {

    private final double minPrice;

    private final double maxPrice;

    public PriceCompanyFilter(final double minPrice, final double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean filtredIn(final Company company) {
        if (company.getPrice() >= minPrice && company.getPrice() <= maxPrice) {
            return true;
        }
        return false;
    }
}
