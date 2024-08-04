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

describe('Percurso controller', function () {
	const sandbox = sinon.createSandbox();

	beforeEach(function() {
		
		Container.reset();
		let PercursoSchemaInstance = require("../src/persistence/schemas/PercursoSchema").default;
		Container.set("percursoSchema", PercursoSchemaInstance);

		Container.set("logger",Logger);
		
		
		let PercursoRepoClass = require("../src/repos/PercursoRepo").default;
		let PercursoRepoInstance = Container.get(PercursoRepoClass);
		Container.set("PercursoRepo", PercursoRepoInstance);
		

		let PercursoServiceClass = require("../src/services/PercursoService").default;
		let PercursoServiceInstance = Container.get(PercursoServiceClass);
		Container.set("PercursoService", PercursoServiceInstance);
		
    })
	afterEach(function() {
		sandbox.restore();
	});

    it('percursoController create unit test using percursoService stub', async function () {
		// Arrange
        let body = { "armazemPartida": "X14",
        "armazemChegada": "X16",
        "distancia": 3,
        "tempo": 4,
        "energiaGasta": 5,
        "tempoExtra": 4};
        let req: Partial<Request> = {};
		
		req.body = body;
        let res: Partial<Response> = {
			json: sinon.spy()
        };
		let next: Partial<NextFunction> = () => {};

		let percursoServiceInstance = Container.get("PercursoService");
		sinon.stub(percursoServiceInstance, "createPercurso").returns( Result.ok<IPercursoDTO>( {
		"armazemPartida": req.body.armazemPartida,
		"armazemChegada":req.body.armazemChegada,
		"distancia":req.body.distancia,
		"tempo":req.body.tempo,
		"energiaGasta":req.body.energiaGasta,
		"tempoExtra":req.body.tempoExtra
		} ));

		const ctrl = new PercursoController(percursoServiceInstance as IPercursoService);

		// Act
		let percursoCriado = await ctrl.createPercurso(<Request>req, <Response>res, <NextFunction>next);


		// Assert
		
		sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match({
        "armazemPartida": req.body.armazemPartida,
        "armazemChegada":req.body.armazemChegada,
        "distancia":req.body.distancia,
        "tempo":req.body.tempo,
        "energiaGasta":req.body.energiaGasta,
        "tempoExtra":req.body.tempoExtra}));
	});
	
	it('percursoController + percursoService integration test using percursoRepository and percurso stubs', async function () {	
		// Arrange	
        let body = { "armazemPartida": "XX4",
        "armazemChegada": "XX6",
        "distancia": 3,
        "tempo": 4,
        "energiaGasta": 5,
        "tempoExtra": 4};
        let req: Partial<Request> = {};
		req.body = body;
			
        let res: Partial<Response> = {
			status:sinon.spy(),
			
        };
		let next: Partial<NextFunction> = () => {};
		
		sinon.stub(Percurso, "create").returns(Result.ok({
            "armazemPartida": req.body.armazemPartida,
            "armazemChegada":req.body.armazemChegada,
            "distancia":req.body.distancia,
            "tempo":req.body.tempo,
            "energiaGasta":req.body.energiaGasta,
            "tempoExtra":req.body.tempoExtra}));

		let percursoRepoInstance = Container.get("PercursoRepo");
		sinon.stub(percursoRepoInstance, "create").returns(new Promise<Percurso>((resolve, reject) => {
			resolve(Percurso.create({
                "armazemPartida": req.body.armazemPartida,
                "armazemChegada":req.body.armazemChegada,
                "distancia":req.body.distancia,
                "tempo":req.body.tempo,
                "energiaGasta":req.body.energiaGasta,
                "tempoExtra":req.body.tempoExtra}).getValue())
		}));

		let percursoServiceInstance = Container.get("PercursoService");

		const ctrl = new PercursoController(percursoServiceInstance as IPercursoService);
		let serviceSpy= sinon.spy(percursoServiceInstance,"createPercurso");

		// Act
		let  percursoCriado =await ctrl.createPercurso(<Request>req, <Response>res, <NextFunction>next);

		// Assert
		
		
		
		sinon.assert.calledOnce(serviceSpy);
		sinon.assert.calledWith(serviceSpy,sinon.match({
            "armazemPartida": req.body.armazemPartida,
            "armazemChegada":req.body.armazemChegada,
            "distancia":req.body.distancia,
            "tempo":req.body.tempo,
            "energiaGasta":req.body.energiaGasta,
            "tempoExtra":req.body.tempoExtra}))
		});
	})