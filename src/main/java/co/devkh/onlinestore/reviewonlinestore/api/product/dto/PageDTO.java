package co.devkh.onlinestore.reviewonlinestore.api.product.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDTO {
	private List<?> list; 
	private PaginationDTO pagination;
	
	public PageDTO(Page<?> page) {
		//return content of the page
		this.list = page.getContent();
		this.pagination = PaginationDTO.builder()
				.empty(page.isEmpty())
				.first(page.isFirst())
				.last(page.isLast())
				.pageSize(page.getPageable().getPageSize())
				.pageNumber(page.getPageable().getPageNumber() + 1)
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.numberOfElements(page.getNumberOfElements())
				.build();
	}
}
