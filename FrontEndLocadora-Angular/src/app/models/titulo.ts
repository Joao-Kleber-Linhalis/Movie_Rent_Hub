import { Ator } from "./ator";
import { Classe } from "./classe";
import { Diretor } from "./diretor";
import { Item } from "./item";

export interface Titulo{
    id?: any,
    nome: string;
    ano: number;
    sinopse: string;
    capa?:any,
    categorias: string[];
    items?: Item[];
    diretor: Diretor;
    classe: Classe;
    atores?: Ator[];
    ativo?: Boolean
}