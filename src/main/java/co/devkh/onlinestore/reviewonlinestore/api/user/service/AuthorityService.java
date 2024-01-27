package co.devkh.onlinestore.reviewonlinestore.api.user.service;

import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;

public interface AuthorityService {
    /**
     * Get all authorities
     * @return list of authorities
     */
    StructureRS getAllAuthorities(BaseListingRQ request);

}
