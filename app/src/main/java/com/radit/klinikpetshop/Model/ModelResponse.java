package com.radit.klinikpetshop.Model;

import java.util.List;

public class ModelResponse {

    private String kode, pesan;
    private List<ModelPetShop> data;

    public String getKode(){return kode;}
    public String getPesan(){return pesan;}

    public List<ModelPetShop> getData(){
        return data;
    }
}
