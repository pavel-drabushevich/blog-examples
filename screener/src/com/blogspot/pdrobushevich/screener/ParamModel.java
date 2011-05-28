package com.blogspot.pdrobushevich.screener;

import javax.servlet.http.HttpServletRequest;

public class ParamModel {

    private final String echo;

    private final int displayLength;

    private final int displayStart;

    private final int sortColumnIndex;

    private final String sortDirection;

    private final double minPrice;

    private final double maxPrice;

    public ParamModel(final String echo, final int displayLength, final int displayStart, final int sortColumnIndex,
            final String sortDirection, final double minPrice, final double maxPrice) {
        this.echo = echo;
        this.displayLength = displayLength;
        this.displayStart = displayStart;
        this.sortColumnIndex = sortColumnIndex;
        this.sortDirection = sortDirection;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getEcho() {
        return echo;
    }

    public int getDisplayLength() {
        return displayLength;
    }

    public int getDisplayStart() {
        return displayStart;
    }

    public int getSortColumnIndex() {
        return sortColumnIndex;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public static ParamModel parseFromRequest(final HttpServletRequest request) {
        final String echo = request.getParameter("sEcho");
        final int displayStart = getIntParameter(request, "iDisplayStart", 0);
        final int displayLength = getIntParameter(request, "iDisplayLength", 10);
        final int sortColumnIndex = getIntParameter(request, "iSortCol_0", 0);
        final String sortDirection = request.getParameter("sSortDir_0");
        final double minPrice = getDoubleParameter(request, "dMinPrice", Double.MIN_VALUE);
        final double maxPrice = getDoubleParameter(request, "dMaxPrice", Double.MAX_VALUE);
        return new ParamModel(echo, displayLength, displayStart, sortColumnIndex, sortDirection, minPrice, maxPrice);
    }

    private static int getIntParameter(final HttpServletRequest request, final String name, final int defaultValue) {
        final String value = request.getParameter(name);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static double getDoubleParameter(final HttpServletRequest request, final String name,
            final double defaultValue) {
        final String value = request.getParameter(name);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
