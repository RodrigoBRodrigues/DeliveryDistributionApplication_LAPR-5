import { AggregateRoot } from '../../core/domain/AggregateRoot';
import { UniqueEntityID } from '../../core/domain/UniqueEntityID';
import { Result } from '../../core/logic/Result';
import { PercursoId } from './percursoId';
import { IPercursoDTO } from '../../dto/IPercursoDTO';


interface PercursoProps {
  armazemPartida: string;
  armazemChegada: string;
  distancia: number;
  tempo: number;
  energiaGasta: number;
  tempoExtra: number;
}

export class Percurso extends AggregateRoot<PercursoProps> {
  get id(): UniqueEntityID {
    return this._id;
  }

  get percursoId(): PercursoId {
    return new PercursoId(this._id);
  }

  get armazemPartida(): string {
    return this.props.armazemPartida;
  }

  get armazemChegada(): string {
    return this.props.armazemChegada;
  }

  get distancia(): number {
    return this.props.distancia;
  }

  get tempo(): number {
    return this.props.tempo;
  }

  get energiaGasta(): number {
    return this.props.energiaGasta;
  }

  get tempoExtra(): number {
    return this.props.tempoExtra;
  }

  set armazemPartida(value: string) {
    this.props.armazemPartida = value;
  }

  set armazemChegada(value: string) {
    this.props.armazemChegada = value;
  }

  set distancia(value: number) {
    this.props.distancia = value;
  }

  set tempo(value: number) {
    this.props.tempo = value;
  }

  set energiaGasta(value: number) {
    this.props.energiaGasta = value;
  }

  set tempoExtra(value: number) {
    this.props.tempoExtra = value;
  }

  private constructor(props: PercursoProps, id?: UniqueEntityID) {
    super(props, id);
  }

  public static create(percursoDTO: IPercursoDTO, id?: UniqueEntityID): Result<Percurso> {
    const percurso = new Percurso(
      {
        armazemPartida: percursoDTO.armazemPartida,
        armazemChegada: percursoDTO.armazemChegada,
        distancia: percursoDTO.distancia,
        tempo: percursoDTO.tempo,
        energiaGasta: percursoDTO.energiaGasta,
        tempoExtra: percursoDTO.tempoExtra,
      },
      id,
    );

    return Result.ok<Percurso>(percurso);
  }
}
