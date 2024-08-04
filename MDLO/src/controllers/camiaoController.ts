import { Request, Response, NextFunction } from 'express';
import { Inject, Service } from 'typedi';
import config from '../../config';

import ICamiaoService from '../services/IServices/ICamiaoService';
import { ICamiaoDTO } from '../dto/ICamiaoDTO';

import { Result } from '../core/logic/Result';
import ICamiaoController from './IControllers/ICamiaoController';
import { Logger } from 'winston';
import { createCipheriv } from 'crypto';
import { List } from 'lodash';
@Service()
export default class CamiaoController implements ICamiaoController {
  constructor(@Inject(config.services.camiao.name) private CamiaoServiceInstance: ICamiaoService) {}

  public async createCamiao(req: Request, res: Response, next: NextFunction) {
    try {
      //erro no debaixo
      const camiaoOrError = (await this.CamiaoServiceInstance.createCamiao(req.body as ICamiaoDTO)) as Result<
        ICamiaoDTO
      >;

      if (camiaoOrError.isFailure) {
        return res.status(404).send();
      }

      const camiaoDTO = camiaoOrError.getValue();

      return res.json(camiaoDTO).status(200);
    } catch (e) {
      return next(e);
    }
  }

  public async getCamiaoById(req: Request, res: Response, next: NextFunction) {
    try {
      const aux = req.url.substring(4, req.url.length);

      const camiaoOrError = (await this.CamiaoServiceInstance.getCamiao(aux)) as Result<ICamiaoDTO>;

      if (camiaoOrError.isFailure) {
        return res.status(404).send();
      }

      const camiaoDTO = camiaoOrError.getValue();
      return res.json(camiaoDTO).status(201);
    } catch (e) {
      return next(e);
    }
  }

  public async getCamiaoByMatricula(req: Request, res: Response, next: NextFunction) {
    try {
      const aux = req.url.substring(12, req.url.length);

      const camiaoOrError = (await this.CamiaoServiceInstance.getCamiaoByMatricula(aux)) as Result<ICamiaoDTO>;

      if (camiaoOrError.isFailure) {
        return res.status(404).send();
      }

      const camiaoDTO = camiaoOrError.getValue();
      return res.json(camiaoDTO).status(201);
    } catch (e) {
      return next(e);
    }
  }

  public async updateCamiao(req: Request, res: Response, next: NextFunction) {
    console.log('boas');
    try {
      const aux = req.url.substring(12, req.url.length);
      const CamiaoOrError = (await this.CamiaoServiceInstance.updateCamiao(req.body as ICamiaoDTO, aux)) as Result<
        ICamiaoDTO
      >;

      if (CamiaoOrError.isFailure) {
        return res.status(404).send();
      }

      const CamiaoDTO = CamiaoOrError.getValue();
      return res.status(201).json(CamiaoDTO);
    } catch (e) {
      return next(e);
    }
  }

  public async getAllCamioes(req: Request, res: Response, next: NextFunction) {
    try {
      const camioesOrError = (await this.CamiaoServiceInstance.getCamioes()) as Result<ICamiaoDTO[]>;
      if (camioesOrError.isFailure) {
        return res.status(402).send();
      }
      const camioesDTO = camioesOrError.getValue();
      return res.json(camioesDTO).status(201);
    } catch (e) {
      return next(e);
    }
  }

  public async deleteCamiaoByMatricula(req: Request, res: Response, next: NextFunction) {
    try {
      const aux = req.url.substring(12, req.url.length);

      const camiaoOrError = (await this.CamiaoServiceInstance.deleteCamiaoByMatricula(aux)) as Result<ICamiaoDTO>;

      if (camiaoOrError.isFailure) {
        return res.status(402).send();
      }
      const camiaoDTO = camiaoOrError.getValue();
      return res.json(camiaoDTO).status(200);
    } catch (e) {
      return next(e);
    }
  }
  public async patchCamiao(req: Request, res: Response, next: NextFunction) {
    console.log('boas');
    try {
      const aux = req.url.substring(12, req.url.length);
      const valor = req.body.ativo as boolean;
      const CamiaoOrError = (await this.CamiaoServiceInstance.patchCamiao(aux, valor)) as Result<ICamiaoDTO>;

      if (CamiaoOrError.isFailure) {
        return res.status(404).send();
      }

      const CamiaoDTO = CamiaoOrError.getValue();
      return res.status(201).json(CamiaoDTO);
    } catch (e) {
      return next(e);
    }
  }
}
