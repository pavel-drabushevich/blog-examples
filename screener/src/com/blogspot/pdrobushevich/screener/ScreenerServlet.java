package com.blogspot.pdrobushevich.screener;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.blogspot.pdrobushevich.screener.screener.data.Company;
import com.blogspot.pdrobushevich.screener.screener.data.CompanyComparator;
import com.blogspot.pdrobushevich.screener.screener.data.CompanyRepository;
import com.blogspot.pdrobushevich.screener.screener.data.CompanyRepository.CompanyResult;
import com.blogspot.pdrobushevich.screener.screener.data.PriceCompanyFilter;

@WebServlet(urlPatterns = { "/data" })
public class ScreenerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static String[] columns;

    private final static CompanyRepository companyRepository = new CompanyRepository();

    static {
        columns = new String[4];
        columns[0] = "Company name";
        columns[1] = "Symbol";
        columns[2] = "Market cap.";
        columns[3] = "Price";
    }

    public ScreenerServlet() {
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
            IOException {
        ParamModel paramModel = ParamModel.parseFromRequest(request);

        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.print(prepareData(paramModel));
        writer.flush();
        writer.close();
    }

    private String prepareData(final ParamModel paramModel) {
        final PriceCompanyFilter companyFilter = new PriceCompanyFilter(paramModel.getMinPrice(),
                paramModel.getMaxPrice());

        final String sortColumn = columns[paramModel.getSortColumnIndex()];
        final int sortDirection = paramModel.getSortDirection().equals("asc") ? 1 : -1;
        final CompanyComparator companyComparator = new CompanyComparator(sortColumn, sortDirection);

        CompanyResult companyResult = companyRepository.get(paramModel.getDisplayStart(),
                paramModel.getDisplayLength(), companyComparator, companyFilter);

        final JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("sEcho", paramModel.getEcho());
            jsonResponse.put("iTotalRecords", companyResult.getTotal());
            jsonResponse.put("iTotalDisplayRecords", companyResult.getFilteredIn());

            final JSONArray jsonCompanies = new JSONArray();

            for (final Company company : companyResult.getCompanies()) {
                final JSONArray jsonCompany = new JSONArray();
                jsonCompany.put(company.getName()).put(company.getSymbol()).put(company.getFormatedMktCap())
                        .put(company.getPrice());
                jsonCompanies.put(jsonCompany);
            }

            jsonResponse.put("aaData", jsonCompanies);
        } catch (JSONException e) {
            return null;
        }

        return jsonResponse.toString();
    }

}
