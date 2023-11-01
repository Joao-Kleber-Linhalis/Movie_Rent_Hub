import { Cliente } from "./cliente";

export interface Dependente{
    id?: any,
    nome: string,
    sexo: string,
    cpf: string,
    nascimento: string,
    ativo?: Boolean,
    cliente: Cliente,
}