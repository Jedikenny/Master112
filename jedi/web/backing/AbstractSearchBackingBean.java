/*
 * JEDI
 *
 *     Copyright (C) 2013  Senato della Repubblica
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.senato.jedi.web.backing;

import it.senato.jedi.bean.ReusableTransformerBean;
import it.senato.jedi.ejb.JEDIConfigDelegate;
import it.senato.jedi.util.base.bean.AbstractOidBean;
import it.senato.jedi.web.util.GeneralComparator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSearchBackingBean<T extends AbstractOidBean> extends AbstractBackingBean {

    public AbstractSearchBackingBean(){
        resultsBuffer = new ArrayList<T>();
        // Set default values somehow (properties files?).
        rowsPerPage = 7; // Default rows per page (max amount of rows to be displayed at once).
        pageRange = 10; // Default page range (max amount of page links to be displayed at once).    
    }

    private boolean searchDone;

    public List<T> queryResults;

    public List<T> resultsBuffer;

    protected int totalRows;

    // Paging.
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;

    // Sorting.
    private String sortField;
    private boolean sortAscending;

    // Paging actions -----------------------------------------------------------------------------

    public void pageFirst() {
        page(0);
    }

    public void pageNext() {
        page(firstRow + rowsPerPage);
    }

    public void pagePrevious() {
        page(firstRow - rowsPerPage);
    }

    public void pageLast() {
        page(totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage));
    }

    public void page(ActionEvent event) {
        page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
    }

    private void page(int firstRow) {
        this.firstRow = firstRow;
        loadDataList(); // Load requested page.
    }

    // Sorting actions ----------------------------------------------------------------------------

    public void sort(ActionEvent event) {
        String sortFieldAttribute = (String) event.getComponent().getAttributes().get("sortField");

        // If the same field is sorted, then reverse order, else sort the new field ascending.
        if (sortField!=null && sortField.equals(sortFieldAttribute)) {
            sortAscending = !sortAscending;
        } else {
            sortField = sortFieldAttribute;
            sortAscending = true;
        }

        pageFirst(); // Go to first page and load requested page.
    }


    // Search actions ----------------------------------------------------------------------------
    public void search() {

        loadAllData();
        searchDone = true;
        pageFirst();

    }
    
    public abstract Class<AbstractOidBean> getClassT();


    // Buffer Loaders ------------------------------------------------------------------------------------
    public void loadDataList() {

        //sort
        if (sortField!=null)
            Collections.sort(queryResults, new GeneralComparator<AbstractOidBean>(sortField, getClassT(), !sortAscending));

        resultsBuffer = new ArrayList<T>();
        for (int i=firstRow; i<firstRow+rowsPerPage && i < totalRows; i++)
            resultsBuffer.add(queryResults.get(i));

        // Set currentPage, totalPages and pages.
        currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
        totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
        int pagesLength = Math.min(pageRange, totalPages);
        pages = new Integer[pagesLength];

        // firstPage must be greater than 0 and lesser than totalPages-pageLength.
        int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);

        // Create pages (page numbers for page links).
        for (int i = 0; i < pagesLength; i++) {
            pages[i] = ++firstPage;
        }
    }

    //DAO Methods

    public abstract void loadAllData();
    //END DAO Methods



// Getters ------------------------------------------------------------------------------------
    public List<T> getResultsBuffer() {
        return resultsBuffer;
    }


    public int getTotalRows() {
        return totalRows;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public Integer[] getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isSearchDone() {
        return searchDone;
    }


// Setters ------------------------------------------------------------------------------------

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public List<T> getQueryResults() {
        return queryResults;
    }
}
