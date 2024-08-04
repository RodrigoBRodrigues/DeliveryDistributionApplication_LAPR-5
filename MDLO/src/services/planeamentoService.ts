/* eslint-disable prettier/prettier */
/* eslint-disable prefer-const */
/* eslint-disable prettier/prettier */
import { Service, Inject } from 'typedi';
import config from '../../config';
import { IPlaneamentoDTO } from '../dto/IPlaneamentoDTO';
import { Planeamento } from '../domain/planeamento';
import IPlaneamentoRepo from '../services/IRepos/IPlaneamentoRepo';
import IPlaneamentoService from './IServices/IPlaneamentoService';
import { Result } from '../core/logic/Result';
import { PlaneamentoMap } from '../mappers/planeamentoMap';
import { Console } from 'console';

@Service()
export default class PlaneamentoService implements IPlaneamentoService {
  constructor(@Inject(config.repos.planeamento.name) private PlaneamentoRepo: IPlaneamentoRepo) {}

  public async getPlaneamento(PlaneamentoId: string): Promise<Result<IPlaneamentoDTO>> {
    try {
      const Planeamento = await this.PlaneamentoRepo.findById(PlaneamentoId);

      if (Planeamento === null) {
        return Result.fail<IPlaneamentoDTO>('Planeamento not found');
      } else {
        const PlaneamentoDTOResult = PlaneamentoMap.toDTO(Planeamento) as IPlaneamentoDTO;
        return Result.ok<IPlaneamentoDTO>(PlaneamentoDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  public async createPlaneamento(PlaneamentoDTO: IPlaneamentoDTO): Promise<Result<IPlaneamentoDTO>> {
    try {
      const PlaneamentoOrError = await Planeamento.create(PlaneamentoDTO);

      if (PlaneamentoOrError.isFailure) {
        return Result.fail<IPlaneamentoDTO>('PlaneamentoOrError.errorValue()');
      }

      const PlaneamentoResult = PlaneamentoOrError.getValue();
      //
      await this.PlaneamentoRepo.create(PlaneamentoResult);

      const PlaneamentoDTOResult = PlaneamentoMap.toDTO(PlaneamentoResult) as IPlaneamentoDTO;
      return Result.ok<IPlaneamentoDTO>(PlaneamentoDTOResult);
    } catch (e) {
      throw e;
    }
  }
  //  REVER COM ATENÇAO IMPORTANTE

  public async getPlaneamentos(): Promise<Result<IPlaneamentoDTO[]>> {
    try {
      const camioes = await this.PlaneamentoRepo.findAll();
      if (camioes === null) {
        return Result.fail<IPlaneamentoDTO[]>('Camioes not found');
      } else {
        let array: IPlaneamentoDTO[] = [];
        camioes.forEach(element => {
          array.push(PlaneamentoMap.toDTO(element));
        });
        return Result.ok<IPlaneamentoDTO[]>(array);
      }
    } catch (e) {
      throw e;
    }
  }
}
