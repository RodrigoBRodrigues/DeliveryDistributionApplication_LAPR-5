export interface Posicao{
  id: string
  designacao: string
  longitude : number;
  latitude : number;
  altitude : number;
  armazensAdjacentes: Posicao[];
  url : string [];
}
