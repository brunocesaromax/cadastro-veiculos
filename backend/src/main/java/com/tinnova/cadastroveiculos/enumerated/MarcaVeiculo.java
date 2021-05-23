package com.tinnova.cadastroveiculos.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MarcaVeiculo {
    FIAT("Fiat"),
    CHEVROLET("Chevrolet"),
    VOLKSWAGEN("Volkswagen"),
    HYUNDAI("Hyundai"),
    JEEP("Jeep"),
    RENAULT("Renault"),
    TOYOTA("Toyota"),
    FORD("Ford"),
    HONDA("Honda"),
    CITROEN("Citroen");

    private String value;
}
