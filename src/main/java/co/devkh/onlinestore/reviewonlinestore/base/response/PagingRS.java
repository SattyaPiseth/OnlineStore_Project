package co.devkh.onlinestore.reviewonlinestore.base.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagingRS {
   // private List<?> list;
    private int page;
    private int size;
    private int totalPage;
    private long totals;
    private boolean first;
    private boolean last;
    private boolean empty;

    // PagingRS pagingRS = new PagingRS(employee.getNumber(), employee.getSize(), employee.getTotalPages(), employee.getTotalElements());
    public PagingRS(List<?> list,int page, int size, int totalPage, long total, boolean first, boolean last, boolean empty) {
       // this.list = list;
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
        this.totals = total;
        this.first = first;
        this.last = last;
        this.empty = empty;
    }
    public <T> PagingRS(Page<T> page) {
      //  this.list = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalPage = page.getTotalPages();
        this.totals = page.getTotalElements();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
    }

}
