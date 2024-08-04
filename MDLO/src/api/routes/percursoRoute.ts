import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';
import IPercursoController from '../../controllers/IControllers/IPercursoController';

import config from "../../../config";

const route = Router();

export default (app: Router) => {
  app.use('/percursos', route);

  const ctrl = Container.get(config.controllers.percurso.name) as IPercursoController;

  route.post('',
    celebrate({
      body: Joi.object({
        armazemPartida:Joi.string().required(),
        armazemChegada:Joi.string().required(),
        distancia:Joi.string().required(),
        tempo:Joi.string().required(),
        energiaGasta:Joi.string().required(),
        tempoExtra:Joi.string().required(),
      })
    }),
    (req, res, next) => ctrl.createPercurso(req, res, next) );

    route.get('/armazempartida/:armazemPartida',(req,res,next)=>{ctrl.getPercursoListByArmazemPartida(req,res,next); req.params.armazemPartida})

    route.get('/id/:idPercurso', (req, res, next) => { ctrl.getPercursoById(req, res, next); req.params.idPercurso; } );

    route.get('',(req,res,next)=>{ctrl.getPercursoList(req,res,next);})

    route.get('/armazens',(req, res, next)=> {
      ctrl.getPercursosEntreArmazens(req,res,next);
      req.query.armazemPartida;
      req.query.armazemChegada;

    })

    route.get('/primeiro',(req, res, next)=> {
      ctrl.getPrimeiroPercursoEntreArmazens(req,res,next);
      req.query.armazemPartida;
      req.query.armazemChegada;
    })

    route.delete('/delete',(req, res, next) => { ctrl.deletePercurso(req, res, next); 
      req.query.armazemPartida;
      req.query.armazemChegada;})

    route.put('/edit', 
      celebrate({
        body: Joi.object({
        armazemPartida:Joi.string().required(),
        armazemChegada:Joi.string().required(),
        distancia:Joi.string().required(),
        tempo:Joi.string().required(),
        energiaGasta:Joi.string().required(),
        tempoExtra:Joi.string().required(),
      })
    }),
    (req, res, next) => {ctrl.updatePercurso(req, res, next);       
     //req.query.armazemPartida;
     //req.query.armazemhegada; 
    });

};
