package co.devkh.onlinestore.reviewonlinestore.api.user.service;

import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;

public interface AuthorityService {
    /**
     * Get all authorities  in system by paging and sorting with search
     * @param request {@link BaseListingRQ}
     * @return {@link StructureRS}
     */
    StructureRS getAllAuthorities(BaseListingRQ request);

}
