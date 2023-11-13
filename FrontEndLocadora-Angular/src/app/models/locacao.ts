import { Cliente } from "./cliente";
import { Dependente } from "./dependente";
import { Item } from "./item";

export interface Locacao{
    id? : any,
    cliente : Cliente,
    dependente?: Dependente,
    dtLocacao: string,
    item: Item,
    dtDevolucaoPrevista: string,
    dtDevolucaoEfetiva?: string,
    valorCobrado: number,
    multaCobrada? : number,
    total: number,
}