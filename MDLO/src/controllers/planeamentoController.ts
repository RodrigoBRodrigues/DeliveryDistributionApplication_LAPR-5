import IPlaneamentoController from './IControllers/IPlaneamentoController';
process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';
import { Response, Request, NextFunction } from 'express';
import { Inject, Service } from 'typedi';
import config from '../../config';
import https = require('https');
import fetch = require('node-fetch');
import { IPlaneamentoDTO } from '../dto/IPlaneamentoDTO';

import dns = require('dns');
import IPlaneamentoService from '../services/IServices/IPlaneamentoService';
import { Result } from '../core/logic/Result';
dns.setDefaultResultOrder('ipv4first');
@Service()
export default class PlaneamentoController implements IPlaneamentoController {
  constructor(@Inject(config.services.planeamento.name) private PlaneamentoServiceInstance: IPlaneamentoService) {}
  httpAgent = new https.Agent({});

  public async sendPlaneamento(req: Request, res: Response, next: NextFunction) {
    try {
      const pedido = this.createRequest(req.body as IPlaneamentoDTO);
      const response = await fetch(pedido, {
        method: 'GET',
        agent: this.httpAgent,
      });
      const resposta = await response.json();
      const planeamentoOrError = (await this.PlaneamentoServiceInstance.createPlaneamento(
        req.body as IPlaneamentoDTO,
      )) as Result<IPlaneamentoDTO>;
      if (planeamentoOrError.isFailure) {
        return res.status(404).send();
      }
      console.log(resposta);

      return res.json(resposta).status(200);
    } catch (e) {
      return next(e);
    }
  }
  private createRequest(IPlaneamentoDTO) {
    return config.prolog + '/prolog/planeamento?dia=' + IPlaneamentoDTO.dia + '?&camiao=' + IPlaneamentoDTO.camiao;
  }

  public async sendPlaneamentoHeuristica1(req: Request, res: Response, next: NextFunction) {
    try {
      const pedido = this.createRequestHeuristica1(req.body as IPlaneamentoDTO);
      const response = await fetch(pedido, {
        method: 'GET',
        agent: this.httpAgent,
      });

      const resposta = await response.json();
      const planeamentoDTO: IPlaneamentoDTO = {
        dia: req.body.dia,
        camiao: req.body.camiao,
        curso: resposta,
      };
      const planeamentoOrError = (await this.PlaneamentoServiceInstance.createPlaneamento(planeamentoDTO)) as Result<
        IPlaneamentoDTO
      >;
      if (planeamentoOrError.isFailure) {
        return res.status(404).send();
      }

      return res.json(resposta).status(200);
    } catch (e) {
      return next(e);
    }
  }
  private createRequestHeuristica1(IPlaneamentoDTO) {
    return (
      config.prolog + '/prolog/planeamento/heuristica1?dia=' + IPlaneamentoDTO.dia + '&camiao=' + IPlaneamentoDTO.camiao
    );
  }

  public async sendPlaneamentoHeuristica2(req: Request, res: Response, next: NextFunction) {
    try {
      const pedido = this.createRequestHeuristica2(req.body as IPlaneamentoDTO);
      const response = await fetch(pedido, {
        method: 'GET',
        agent: this.httpAgent,
      });
      const resposta = await response.json();
      const planeamentoDTO: IPlaneamentoDTO = {
        dia: req.body.dia,
        camiao: req.body.camiao,
        curso: resposta,
      };
      const planeamentoOrError = (await this.PlaneamentoServiceInstance.createPlaneamento(planeamentoDTO)) as Result<
        IPlaneamentoDTO
      >;
      if (planeamentoOrError.isFailure) {
        return res.status(404).send();
      }
      console.log(resposta);

      return res.json(resposta).status(200);
    } catch (e) {
      return next(e);
    }
  }
  private createRequestHeuristica2(IPlaneamentoDTO) {
    return (
      config.prolog + '/prolog/planeamento/heuristica2?dia=' + IPlaneamentoDTO.dia + '&camiao=' + IPlaneamentoDTO.camiao
    );
  }
  public async sendPlaneamentoHeuristica3(req: Request, res: Response, next: NextFunction) {
    try {
      const pedido = this.createRequestHeuristica3(req.body as IPlaneamentoDTO);
      const response = await fetch(pedido, {
        method: 'GET',
        agent: this.httpAgent,
      });
      const resposta = await response.json();
      const planeamentoDTO: IPlaneamentoDTO = {
        dia: req.body.dia,
        camiao: req.body.camiao,
        curso: resposta,
      };
      const planeamentoOrError = (await this.PlaneamentoServiceInstance.createPlaneamento(planeamentoDTO)) as Result<
        IPlaneamentoDTO
      >;
      if (planeamentoOrError.isFailure) {
        return res.status(404).send();
      }
      console.log(resposta);

      return res.json(resposta).status(200);
    } catch (e) {
      return next(e);
    }
  }
  private createRequestHeuristica3(IPlaneamentoDTO) {
    return (
      config.prolog + '/prolog/planeamento/heuristica3?dia=' + IPlaneamentoDTO.dia + '&camiao=' + IPlaneamentoDTO.camiao
    );
  }
  public async sendPlaneamentoMock(req: Request, res: Response, next: NextFunction) {
    try {
      const response = ['Matosinhos', 'Porto', 'Vila Nova de Gaia', 'Gondomar', 'Maia', 'Matosinhos'];
      const jsonString = JSON.stringify(Object.assign(response));
      console.log(jsonString);

      return res.json(jsonString).status(200);
    } catch (e) {
      return next(e);
    }
  }
  public async getAllPlaneamentos(req: Request, res: Response, next: NextFunction) {
    try {
      const planeamentosOrError = (await this.PlaneamentoServiceInstance.getPlaneamentos()) as Result<
        IPlaneamentoDTO[]
      >;
      if (planeamentosOrError.isFailure) {
        return res.status(402).send();
      }
      const planeamentosDTO = planeamentosOrError.getValue();
      return res.json(planeamentosDTO).status(201);
    } catch (e) {
      return next(e);
    }
  }

  public async sendPlaneamentoAlgoritmoGenetico(req: Request, res: Response, next: NextFunction) {
    try {
      const pedido = this.createRequestAlgoritmoGenetico(req.body as IPlaneamentoDTO);
      console.log(pedido);
      const response = await fetch(pedido, {
        method: 'GET',
        agent: this.httpAgent,
      });
      console.log(response);
      const resposta = await response.json();
      const planeamentoDTO: IPlaneamentoDTO = {
        dia: req.body.dia,
        camiao: req.body.camiao,
        curso: resposta,
      };
      const planeamentoOrError = (await this.PlaneamentoServiceInstance.createPlaneamento(planeamentoDTO)) as Result<
        IPlaneamentoDTO
      >;
      if (planeamentoOrError.isFailure) {
        return res.status(404).send();
      }
      console.log(resposta);

      return res.json(resposta).status(200);
    } catch (e) {
      return next(e);
    }
  }
  private createRequestAlgoritmoGenetico(IPlaneamentoDTO) {
    return (
      config.prolog + '/prolog/planeamento/algoritmogenetico?dia=' + IPlaneamentoDTO.dia + '&camiao=' + IPlaneamentoDTO.camiao
    );
  }
  public async sendPlaneamentoAlgoritmoGeneticoFrota(req: Request, res: Response, next: NextFunction) {
    try {
      const pedido = this.createRequestAlgoritmoGeneticoFrota(req.body as IPlaneamentoDTO);
      console.log(pedido);
      const response = await fetch(pedido, {
        method: 'GET',
        agent: this.httpAgent,
      });
      console.log(response);
      const resposta = await response.json();
      const planeamentoDTO: IPlaneamentoDTO = {
        dia: req.body.dia,
        camiao: req.body.camiao,
        curso: resposta,
      };
      const planeamentoOrError = (await this.PlaneamentoServiceInstance.createPlaneamento(planeamentoDTO)) as Result<
        IPlaneamentoDTO
      >;
      if (planeamentoOrError.isFailure) {
        return res.status(404).send();
      }
      console.log(resposta);

      return res.json(resposta).status(200);
    } catch (e) {
      return next(e);
    }
  }
  private createRequestAlgoritmoGeneticoFrota(IPlaneamentoDTO) {
    return (
      config.prolog + '/prolog/planeamento/algoritmogeneticofrota?dia=' + IPlaneamentoDTO.dia + '&camiao=' + IPlaneamentoDTO.camiao
    );
  }
}
