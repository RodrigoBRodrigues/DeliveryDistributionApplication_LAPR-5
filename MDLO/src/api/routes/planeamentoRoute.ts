import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';

import config from '../../../config';
import IPlaneamentoController from '../../controllers/IControllers/IPlaneamentoController';

const route = Router();

export default (app: Router) => {
  app.use('/planeamento', route);

  const ctrl = Container.get(config.controllers.planeamento.name) as IPlaneamentoController;

  route.post(
    '',
    celebrate({
      body: Joi.object({
        dia: Joi.number().required(),
        camiao: Joi.string().required(),
      }),
    }),
    (req, res, next) => ctrl.sendPlaneamento(req, res, next),
  );

  route.post(
    '/heuristica1',
    celebrate({
      body: Joi.object({
        dia: Joi.number().required(),
        camiao: Joi.string().required(),
      }),
    }),
    (req, res, next) => ctrl.sendPlaneamentoHeuristica1(req, res, next),
  );

  route.post(
    '/heuristica2',
    celebrate({
      body: Joi.object({
        dia: Joi.number().required(),
        camiao: Joi.string().required(),
      }),
    }),
    (req, res, next) => ctrl.sendPlaneamentoHeuristica2(req, res, next),
  );
  route.post(
    '/heuristica3',
    celebrate({
      body: Joi.object({
        dia: Joi.number().required(),
        camiao: Joi.string().required(),
      }),
    }),
    (req, res, next) => ctrl.sendPlaneamentoHeuristica3(req, res, next),
  );
  route.post(
    '/algoritmogenetico',
    celebrate({
      body: Joi.object({
        dia: Joi.number().required(),
        camiao: Joi.string().required(),
      }),
    }),
    (req, res, next) => ctrl.sendPlaneamentoAlgoritmoGenetico(req,res,next),
  );
  route.post(
    '/algoritmogeneticofrota',
    celebrate({
      body: Joi.object({
        dia: Joi.number().required(),
        camiao: Joi.string().required(),
      }),
    }),
    (req, res, next) => ctrl.sendPlaneamentoAlgoritmoGeneticoFrota(req,res,next),
  );
  route.get(
    '/heuristicaMock',
    celebrate({
      body: Joi.object({
        dia: Joi.number().required(),
        camiao: Joi.string().required(),
      }),
    }),
    (req, res, next) => ctrl.sendPlaneamentoMock(req, res, next),
  );
  route.get('/all', (req, res, next) => {
    ctrl.getAllPlaneamentos(req, res, next);
  });
 
};
