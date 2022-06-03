package com.example.server.serviceLayer.FacadeObjects.PolicyFacade;

import com.example.server.businessLayer.Market.Policies.DiscountPolicy.DiscountState.DiscountLevelState;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class LevelDeserializer extends StdDeserializer<DiscountLevelStateFacade>{

    protected LevelDeserializer(Class<?> vc) {
        super ( vc );
    }

    protected LevelDeserializer(JavaType valueType) {
        super ( valueType );
    }

    protected LevelDeserializer(StdDeserializer<?> src) {
        super ( src );
    }

    @Override
    public DiscountLevelStateFacade deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return new ShopLevelStateFacade();
    }

//    private SimpleDiscountFacade desSimple(JsonParser jsonParser, DeserializationContext deserializationContext){
//        System.out.println ("simple" );
//        return null;
//    }
//
//    private SimpleDiscountFacade desComp(JsonParser jsonParser, DeserializationContext deserializationContext){
//        System.out.println ("composite" );
//        return null;
//    }
}
