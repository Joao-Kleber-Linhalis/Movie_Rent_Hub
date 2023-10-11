import { Titulo } from "./titulo";

export interface Item{
    id?: any;
    numSerie: number;
    dtAquisicao: string;
    titulo: Titulo;
    tipoItem: string;
    statusItem: string;
    ativo?: Boolean
}