package org.app.app.mapper;

import org.app.app.dto.MarketRequest;
import org.app.app.dto.MarketResponse;
import org.app.app.model.Market;

public class MarketMapper {

    public static Market toEntity(MarketRequest request){
        Market m = new Market();
        m.setName(request.getName());
        m.setAddress(request.getAddress());
        m.setOrganization(request.getOrganization());
        m.setZip(request.getZip());
        m.setCity(request.getCity());
        m.setCountry(request.getCountry());
        return m;
    }

    public static MarketResponse toResponse(Market m){
        return new MarketResponse(
                m.getId(),
                m.getName(),
                m.getOrganization(),
                m.getAddress(),
                m.getCity(),
                m.getZip(),
                m.getCountry()
        );
    }
}
