import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';
import ICamiaoController from '../../controllers/IControllers/ICamiaoController';

import config from '../../../config';

const route = Router();

export default (app: Router) => {
  app.use('/camioes', route);

  const ctrl = Container.get(config.controllers.camiao.name) as ICamiaoController;

  route.post(
    '',
    celebrate({
      body: Joi.object({
        matricula: Joi.string().required(),
        caracteristica: Joi.string().required(),
        tara: Joi.number().required(),
        capacidade: Joi.number().required(),
        carga: Joi.number().required(),
        autonomia: Joi.number().required(),
        tempo: Joi.number().required(),
        ativo: Joi.boolean().required(),
      }),
    }),
    (req, res, next) => ctrl.createCamiao(req, res, next),
  );

  route.put(
    '/matriculas/:matricula',
    celebrate({
      body: Joi.object({
        caracteristica: Joi.string().required(),
        tara: Joi.number().required(),
        capacidade: Joi.number().required(),
        carga: Joi.number().required(),
        autonomia: Joi.number().required(),
        tempo: Joi.number().required(),
        ativo: Joi.boolean().required(),
      }),
    }),
    (req, res, next) => {
      ctrl.updateCamiao(req, res, next);
      req.params.matricula;
    },
  );

  route.get('/id/:idCamiao', (req, res, next) => {
    ctrl.getCamiaoById(req, res, next);
    req.params.idCamiao;
  });

  route.get('', (req, res, next) => {
    ctrl.getAllCamioes(req, res, next);
  });

  route.get('/matriculas/:matricula', (req, res, next) => {
    ctrl.getCamiaoByMatricula(req, res, next);
    req.params.matricula;
  });

  route.delete('/matriculas/:matricula', (req, res, next) => {
    ctrl.deleteCamiaoByMatricula(req, res, next);
    req.params.matricula;
  });

  route.patch(
    '/matriculas/:matricula',
    celebrate({
      body: Joi.object({
        ativo: Joi.boolean().required(),
      }),
    }),
    (req, res, next) => {
      ctrl.patchCamiao(req, res, next);
      req.params.matricula;
    },
  );
};
