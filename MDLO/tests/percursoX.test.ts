import 'reflect-metadata';

import * as sinon from 'sinon';
import { Response, Request, NextFunction } from 'express';
import { Container, Service } from 'typedi';
import { Result } from '../src/core/logic/Result';
import IPercursoService from "../src/services/IServices/IPercursoService";
import PercursoController from "../src/controllers/percursoController";
import {IPercursoDTO} from '../src/dto/IPercursoDTO';
import {Percurso} from '../src/domain/Percursos/percurso';
import { doesNotMatch } from 'assert';
import { Logger } from 'winston';
import PercursoService from '../src/services/percursoService';
import IPercursoRepo from '../src/services/IRepos/IPercursoRepo';


describe('Percurso ', function () {
	const sandbox = sinon.createSandbox();
    let body = { "armazemPartida": "X14",
    "armazemChegada": "X16",
    "distancia": 3,
    "tempo": 4,
    "energiaGasta": 5,
    "tempoExtra": 4};

    let  percursoCriado=Percurso.create(body as IPercursoDTO);
	beforeEach(function() {
		
		
    })

	afterEach(function() {
		sandbox.restore();
	});

    it('Percurso get tests',async function name() {
        
        sinon.assert.match(body.armazemPartida,percursoCriado.getValue().armazemPartida)
        sinon.assert.match(body.armazemChegada,percursoCriado.getValue().armazemChegada)
        sinon.assert.match(body.distancia,percursoCriado.getValue().distancia)
        sinon.assert.match(body.tempo,percursoCriado.getValue().tempo)
        sinon.assert.match(body.energiaGasta,percursoCriado.getValue().energiaGasta)
        sinon.assert.match(body.tempoExtra,percursoCriado.getValue().tempoExtra)
        
        
    })

    it('Percurso set tests',async function name(){
        let body2 = { "armazemPartida": "XX1",
        "armazemChegada": "X16",
        "distancia": 3,
        "tempo": 4,
        "energiaGasta": 5,
        "tempoExtra": 4  };
        
        percursoCriado.getValue().armazemPartida=body2.armazemPartida
        percursoCriado.getValue().armazemChegada=body2.armazemChegada
        percursoCriado.getValue().distancia=body2.distancia
        percursoCriado.getValue().tempo=body2.tempo
        percursoCriado.getValue().energiaGasta=body2.energiaGasta;
        percursoCriado.getValue().tempoExtra=body2.tempoExtra;
       

        sinon.assert.match(percursoCriado.getValue().armazemPartida,body2.armazemPartida);
        sinon.assert.match(percursoCriado.getValue().armazemChegada,body2.armazemChegada);
        sinon.assert.match(percursoCriado.getValue().distancia,body2.distancia);
        sinon.assert.match(percursoCriado.getValue().tempo,body2.tempo);
        sinon.assert.match(percursoCriado.getValue().energiaGasta,body2.energiaGasta);
        sinon.assert.match(percursoCriado.getValue().tempoExtra,body2.tempoExtra);
    })
})