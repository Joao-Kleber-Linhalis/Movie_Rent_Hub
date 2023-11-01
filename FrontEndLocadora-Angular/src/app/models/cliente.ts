import { Dependente } from "./dependente";

export interface Cliente{
    id?: any,
    nome: string,
    endereco: string,
    telefone: string,
    sexo: string,
    cpf: string,
    nascimento: string,
    ativo?: Boolean,
    dependentes?: Dependente[],
}