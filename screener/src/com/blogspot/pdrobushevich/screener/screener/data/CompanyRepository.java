package com.blogspot.pdrobushevich.screener.screener.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CompanyRepository {

    private final List<Company> companies;

    public CompanyRepository() {
        companies = new ArrayList<Company>();
        companies.add(new Company("Microsoft Corporation", "MSFT", 208.80, 24.76));
        companies.add(new Company("Oracle Corporation", "ORCL", 170.54, 33.70));
        companies.add(new Company("QUALCOMM, Inc.", "QCOM", 95.75, 57.35));
        companies.add(new Company("Cisco Systems, Inc.", "CSCO", 90.53, 16.46));
        companies.add(new Company("Comcast Corporation", "CMCSA", 68.63, 24.89));
        companies.add(new Company("News Corporation", "NWSA", 47.23, 17.98));
        companies.add(new Company("International Business Machines Corp.", "IBM", 204.27, 167.50));
        companies.add(new Company("AT&T Inc.", "T", 185.30, 31.29));
        companies.add(new Company("Vodafone Group Plc (ADR)", "VOD", 144.22, 28.10));
        companies.add(new Company("Siemens AG (ADR)", "SI", 117.19, 128.19));
        companies.add(new Company("Verizon Communications Inc.", "VZ", 102.68, 36.67));
        companies.add(new Company("NTT DoCoMo, Inc. (ADR)", "DCM", 79.62, 18.24));
        companies.add(new Company("SAP AG (ADR)", "SAP", 73.97, 60.29));
        companies.add(new Company("3M Company", "MMM", 66.53, 93.47));
        companies.add(new Company("Texas Instruments Incorporated", "TXN", 40.59, 34.72));
    }

    public CompanyResult get(final int from, final int count, final Comparator<Company> comparator,
            final Filter<Company> filter) {
        final List<Company> filteredIn = filter(companies, filter);
        final List<Company> result = page(sort(filteredIn, comparator), from, count);
        return new CompanyResult(result, companies.size(), filteredIn.size(), result.size());
    }

    private List<Company> page(final List<Company> companies, final int from, final int count) {
        final List<Company> paged = new ArrayList<Company>(count);
        final int viewCount = Math.min(from + count, companies.size());
        for (int i = from; i < viewCount; i++) {
            paged.add(companies.get(i));
        }
        return paged;
    }

    private List<Company> sort(final List<Company> companies, final Comparator<Company> comparator) {
        final List<Company> sorted = new ArrayList<Company>(companies);
        Collections.sort(sorted, comparator);
        return sorted;
    }

    private List<Company> filter(final List<Company> companies, final Filter<Company> filter) {
        final List<Company> filteredIn = new ArrayList<Company>();
        for (final Company company : companies) {
            if (filter.filtredIn(company)) {
                filteredIn.add(company);
            }
        }
        return filteredIn;
    }

    public static class CompanyResult {

        private final List<Company> companies;

        private final int total;

        private final int filteredIn;

        private final int onPage;

        public CompanyResult(final List<Company> companies, final int total, final int filteredIn, final int onPage) {
            this.companies = companies;
            this.total = total;
            this.filteredIn = filteredIn;
            this.onPage = onPage;
        }

        public List<Company> getCompanies() {
            return companies;
        }

        public int getTotal() {
            return total;
        }

        public int getFilteredIn() {
            return filteredIn;
        }

        public int getOnPage() {
            return onPage;
        }

    }

}
