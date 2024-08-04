import { Service, Inject } from 'typedi';
import config from '../../config';
import { ICamiaoDTO } from '../dto/ICamiaoDTO';
import { Camiao } from '../domain/camiao';
import ICamiaoRepo from '../services/IRepos/ICamiaoRepo';
import ICamiaoService from './IServices/ICamiaoService';
import { Result } from '../core/logic/Result';
import { CamiaoMap } from '../mappers/CamiaoMap';
import { Console } from 'console';
import { dadosCamiao } from '../domain/dadosCamiao';

@Service()
export default class CamiaoService implements ICamiaoService {
  constructor(@Inject(config.repos.camiao.name) private CamiaoRepo: ICamiaoRepo) {}

  public async getCamiao(CamiaoId: string): Promise<Result<ICamiaoDTO>> {
    try {
      const Camiao = await this.CamiaoRepo.findById(CamiaoId);

      if (Camiao === null) {
        return Result.fail<ICamiaoDTO>('Camiao not found');
      } else {
        const CamiaoDTOResult = CamiaoMap.toDTO(Camiao) as ICamiaoDTO;
        return Result.ok<ICamiaoDTO>(CamiaoDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  public async getCamiaoByMatricula(matricula: string): Promise<Result<ICamiaoDTO>> {
    try {
      const Camiao = await this.CamiaoRepo.findByMatricula(matricula);

      if (Camiao === null) {
        return Result.fail<ICamiaoDTO>('Camiao not found');
      } else {
        const CamiaoDTOResult = CamiaoMap.toDTO(Camiao) as ICamiaoDTO;
        return Result.ok<ICamiaoDTO>(CamiaoDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  public async createCamiao(CamiaoDTO: ICamiaoDTO): Promise<Result<ICamiaoDTO>> {
    try {
      const CamiaoOrError = await Camiao.create(CamiaoDTO);

      if (CamiaoOrError.isFailure) {
        return Result.fail<ICamiaoDTO>('CamiaoOrError.errorValue()');
      }

      const CamiaoResult = CamiaoOrError.getValue();
      //
      await this.CamiaoRepo.create(CamiaoResult);

      const CamiaoDTOResult = CamiaoMap.toDTO(CamiaoResult) as ICamiaoDTO;
      return Result.ok<ICamiaoDTO>(CamiaoDTOResult);
    } catch (e) {
      throw e;
    }
  }
  //  REVER COM ATENÇAO IMPORTANTE
  public async updateCamiao(CamiaoDTO: ICamiaoDTO, matricula: string): Promise<Result<ICamiaoDTO>> {
    try {
      const Camiao = await this.CamiaoRepo.findByMatricula(matricula);

      if (Camiao === null) {
        return Result.fail<ICamiaoDTO>('Camiao not found');
      } else {
        const dadosCamiaoProps = new dadosCamiao({
          tara: CamiaoDTO.tara,
          capacidade: CamiaoDTO.capacidade,
          carga: CamiaoDTO.carga,
          autonomia: CamiaoDTO.autonomia,
          tempo: CamiaoDTO.tempo,
        });
        Camiao.caracteristica = CamiaoDTO.caracteristica;

        Camiao.dados = dadosCamiaoProps;
        Camiao.ativo = CamiaoDTO.ativo;

        await this.CamiaoRepo.save(Camiao);

        const CamiaoDTOResult = CamiaoMap.toDTO(Camiao) as ICamiaoDTO;
        return Result.ok<ICamiaoDTO>(CamiaoDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }

  public async getCamioes(): Promise<Result<ICamiaoDTO[]>> {
    try {
      const camioes = await this.CamiaoRepo.findAll();
      if (camioes === null) {
        return Result.fail<ICamiaoDTO[]>('Camioes not found');
      } else {
        const array: ICamiaoDTO[] = [];
        camioes.forEach(element => {
          array.push(CamiaoMap.toDTO(element));
        });
        return Result.ok<ICamiaoDTO[]>(array);
      }
    } catch (e) {
      throw e;
    }
  }
  public async deleteCamiaoByMatricula(matricula: string): Promise<Result<ICamiaoDTO>> {
    try {
      const Camiao = await this.CamiaoRepo.deleteCamiaoByMatricula(matricula);

      if (Camiao === null) {
        return Result.fail<ICamiaoDTO>('Camiao not found');
      } else {
        const CamiaoDTOResult = CamiaoMap.toDTO(Camiao) as ICamiaoDTO;
        return Result.ok<ICamiaoDTO>(CamiaoDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }
  public async patchCamiao(matricula: string, ativo: boolean): Promise<Result<ICamiaoDTO>> {
    try {
      const Camiao = await this.CamiaoRepo.findByMatricula(matricula);

      if (Camiao === null) {
        return Result.fail<ICamiaoDTO>('Camiao not found');
      } else {
        Camiao.ativo = ativo;

        await this.CamiaoRepo.save(Camiao);

        const CamiaoDTOResult = CamiaoMap.toDTO(Camiao) as ICamiaoDTO;
        return Result.ok<ICamiaoDTO>(CamiaoDTOResult);
      }
    } catch (e) {
      throw e;
    }
  }
}
