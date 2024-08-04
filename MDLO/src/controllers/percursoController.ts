import { Response, Request, NextFunction } from 'express';
import { Http2ServerResponse } from 'http2';
import { Inject, Service } from 'typedi';
import config from '../../config';
import { Result } from '../core/logic/Result';
import { IPercursoDTO } from '../dto/IPercursoDTO';
import IPercursoService from '../services/IServices/IPercursoService';
import IPercursoController from './IControllers/IPercursoController';



@Service()
export default class PercursoController implements IPercursoController {

    constructor(@Inject(config.services.percurso.name) private PercursoServiceInstance: IPercursoService) {}

    public async createPercurso(req: Request, res: Response, next: NextFunction){

        try {
            const percursoOrError = await this.PercursoServiceInstance.createPercurso(req.body as IPercursoDTO) as Result<IPercursoDTO>;

            if (percursoOrError.isFailure) {
              return res.status(402).send();
            }

            const percursoDTO = percursoOrError.getValue();
            return res.json( percursoDTO ).status(201);
          }
          catch (e) {
            return next(e);
          }
        };



          public async getPercursoById(req: Request, res: Response, next: NextFunction) {
            try {
              let aux = req.url.substring(4,req.url.length);

              const percursoOrError = (await this.PercursoServiceInstance.getPercurso(aux)) as Result<IPercursoDTO>;

              if (percursoOrError.isFailure) {
                return res.status(402).send();
              }

              const percursoDTO = percursoOrError.getValue();
              return res.json(percursoDTO).status(201);
            } catch (e) {
              return next(e);
            }
          }

          public async getPercursoList(req: Request, res: Response, next: NextFunction) {
              try{
                const PercursosOrError=(await this.PercursoServiceInstance.getPercursoList()) as Result<IPercursoDTO[]>;
                if(PercursosOrError.isFailure){
                  return res.status(402).send();
                }
                const PercursosDTO=PercursosOrError.getValue();
                return res.json(PercursosDTO).status(201);
              }catch(e){
                return next(e)
              }
          }

          public async getPercursosEntreArmazens(req: Request, res: Response, next: NextFunction) {
              try{
                let startPoint = req.url.substring(25,28);
                let endPoint = req.url.substring(44,req.url.length);
                console.log(startPoint);
                console.log(endPoint);
                console.log(req.url);

                const PercursosOrError=(await this.PercursoServiceInstance.getPercursosEntreArmazens(startPoint, endPoint)) as Result<IPercursoDTO[]>;
                if(PercursosOrError.isFailure){
                  return res.status(402).send();
                }
                const PercursosDTO=PercursosOrError.getValue();
                return res.json(PercursosDTO).status(201);
              }catch(e){
                return next(e)
              }
          }

          public async deletePercurso(req: Request, res: Response, next: NextFunction){
            try{
              let startPoint = req.url.substring(23,25);
              let endPoint = req.url.substring(42,req.url.length);
              console.log(startPoint);
              console.log(endPoint);
              console.log(req.url);

            const PercursoOrError = (await this.PercursoServiceInstance.deletePercurso(startPoint,endPoint)) as Result<IPercursoDTO>;

            if (PercursoOrError.isFailure) {
              return res.status(402).send();
            }
            const PercursoDTO=PercursoOrError.getValue();
            return res.json(PercursoDTO).status(201);
          }catch(e){
            return next(e)
          }
        }

        public async getPercursoListByArmazemPartida(req: Request, res: Response, next: NextFunction) {
              try{

                let aux = req.url.substring(16,req.url.length);

                const PercursosOrError=(await this.PercursoServiceInstance.getPercursoListByArmazemPartida(aux)) as Result<IPercursoDTO[]>;
                if(PercursosOrError.isFailure){
                  return res.status(402).send();
                }
                const PercursosDTO=PercursosOrError.getValue();
                return res.json(PercursosDTO).status(201);
              }catch(e){
                return next(e)
              }
        }

        public async getPrimeiroPercursoEntreArmazens(req: any, res: any, next: any) {
          try{
            let startPoint = req.url.substring(25,28);
            let endPoint = req.url.substring(44,req.url.length);
            console.log(startPoint);
            console.log(endPoint);
            console.log(req.url);

            const PercursosOrError=(await this.PercursoServiceInstance.getPercursosEntreArmazens(startPoint, endPoint)) as Result<IPercursoDTO[]>;
            if(PercursosOrError.isFailure){
              return res.status(402).send();
            }
            const PercursosDTO=PercursosOrError.getValue();
            return res.json(PercursosDTO).status(201);
          }catch(e){
            return next(e)
          }
      }

      public async updatePercurso(req: Request, res: Response, next: NextFunction) {
          try {

            const PercursoOrError = await this.PercursoServiceInstance.updatePercurso(req.body as IPercursoDTO/*,startPoint,endPoint*/) as Result<IPercursoDTO>;
            if (PercursoOrError.isFailure) {
              return res.status(404).send();
            }
      
            const PercursoDTO = PercursoOrError.getValue();
            return res.status(201).json( PercursoDTO );
          }
          catch (e) {
            return next(e);
          }
        }
    }
