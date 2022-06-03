package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.List;

public class DiscountDeserializer extends StdDeserializer<DiscountTypeFacade> {

    protected DiscountDeserializer(Class<?> vc) {
        super ( vc );
    }

    protected DiscountDeserializer(JavaType valueType) {
        super ( valueType );
    }

    protected DiscountDeserializer(StdDeserializer<?> src) {
        super ( src );
    }

    @Override
    public DiscountTypeFacade deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return new SimpleDiscountFacade ( 50, new ShopLevelStateFacade () );
//        JsonNode jsonNode = jsonParser.getCodec ().readTree ( jsonParser );
//        if (jsonNode.get ( "discountTypes" ) == null || jsonNode.get ( "discountTypes" ).isNull ()){
//            return desSimple ( jsonParser, deserializationContext );
//        } else {
//            return desComp ( jsonParser, deserializationContext );
//        }
    }

    private SimpleDiscountFacade desSimple(JsonParser jsonParser, DeserializationContext deserializationContext){
        System.out.println ("simple" );
        return null;
    }

    private SimpleDiscountFacade desComp(JsonParser jsonParser, DeserializationContext deserializationContext){
        System.out.println ("composite" );
        return null;
    }
}
