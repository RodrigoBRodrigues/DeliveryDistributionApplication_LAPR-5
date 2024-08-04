import { Result } from '../../core/logic/Result';
import { ICamiaoDTO } from '../../dto/ICamiaoDTO';

export default interface ICamiaoService {
  createCamiao(CamiaoDTO: ICamiaoDTO): Promise<Result<ICamiaoDTO>>;
  updateCamiao(CamiaoDTO: ICamiaoDTO, matricula: string): Promise<Result<ICamiaoDTO>>;

  getCamiao(CamiaoId: string): Promise<Result<ICamiaoDTO>>;
  getCamiaoByMatricula(matricula: string): Promise<Result<ICamiaoDTO>>;
  getCamioes(): Promise<Result<ICamiaoDTO[]>>;
  deleteCamiaoByMatricula(matricula: string): Promise<Result<ICamiaoDTO>>;
  patchCamiao(matricula: string, ativo: boolean): Promise<Result<ICamiaoDTO>>;
}
