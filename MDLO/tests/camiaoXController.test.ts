/*import 'reflect-metadata';

import * as sinon from 'sinon';
import { Response, Request, NextFunction } from 'express';
import { Container, Service } from 'typedi';
import { Result } from '../src/core/logic/Result';
import ICamiaoService from "../src/services/IServices/ICamiaoService";
import CamiaoController from "../src/controllers/camiaoController";
import {ICamiaoDTO} from '../src/dto/ICamiaoDTO';
import {Camiao} from '../src/domain/camiao';
import { doesNotMatch } from 'assert';
import { Logger } from 'winston';

describe('Camiao controller', function () {
	const sandbox = sinon.createSandbox();

	beforeEach(function() {
		
		Container.reset();
		let CamiaoSchemaInstance = require("../src/persistence/schemas/CamiaoSchema").default;
		Container.set("camiaoSchema", CamiaoSchemaInstance);

		Container.set("logger",Logger);
		
		
		let CamiaoRepoClass = require("../src/repos/CamiaoRepo").default;
		let CamiaoRepoInstance = Container.get(CamiaoRepoClass);
		Container.set("CamiaoRepo", CamiaoRepoInstance);
		

		let CamiaoServiceClass = require("../src/services/CamiaoService").default;
		let CamiaoServiceInstance = Container.get(CamiaoServiceClass);
		Container.set("CamiaoService", CamiaoServiceInstance);
		
    })
	afterEach(function() {
		sandbox.restore();
	});

    it('camiaoController create unit test using camiaoService stub', async function () {
		// Arrange
        let body = { "matricula":'AA-00-00',
		"caracteristica": "eTruck01",
		 "tara": 7500 ,
		 "capacidade" : 4300 ,
		 "carga" : 80 ,
		 "autonomia" : 100 ,
		 "tempo" : 60  };
        let req: Partial<Request> = {};
		
		req.body = body;
        let res: Partial<Response> = {
			json: sinon.spy()
        };
		let next: Partial<NextFunction> = () => {};

		let camiaoServiceInstance = Container.get("CamiaoService");
		sinon.stub(camiaoServiceInstance, "createCamiao").returns( Result.ok<ICamiaoDTO>( {"id":"123",
		"matricula": req.body.matricula,
		"caracteristica":req.body.caracteristica,
		"tara":req.body.tara,
		"capacidade":req.body.capacidade,
		"carga":req.body.carga,
		"autonomia":req.body.autonomia,
		"tempo":req.body.tempo} ));

		const ctrl = new CamiaoController(camiaoServiceInstance as ICamiaoService);

		// Act
		let camiaoCriado = await ctrl.createCamiao(<Request>req, <Response>res, <NextFunction>next);


		// Assert
		
		sinon.assert.calledOnce(res.json);
		sinon.assert.calledWith(res.json, sinon.match({"id":"123","matricula": req.body.matricula,"caracteristica":req.body.caracteristica,"tara":req.body.tara,"capacidade":req.body.capacidade,"carga":req.body.carga,"autonomia":req.body.autonomia,"tempo":req.body.tempo}));
	});
	/*
	it('camiaoController getByMatricula unit test using camiaoService stub', async function () {
		// Arrange
        let body = { 
			"matricula":'AA-00-00',
		"caracteristica": "eTruck01",
		 "tara": 7500 ,
		 "capacidade" : 4300 ,
		 "carga" : 80 ,
		 "autonomia" : 100 ,
		 "tempo" : 60  };
		 let matricula2="/matriculas/AA-00-00"
        let req: Partial<Request> = {};
		
		req.params={"url":matricula2};
		
        let res: Partial<Response> = {
			json: sinon.spy()
        };
		let next: Partial<NextFunction> = () => {};

		let camiaoServiceInstance = Container.get("CamiaoService");
		let camiaoRepoInstance=Container.get("CamiaoRepo");
		
		sinon.stub(camiaoRepoInstance,"findByMatricula").returns(Camiao.create(body as ICamiaoDTO))

		


		const ctrl = new CamiaoController(camiaoServiceInstance as ICamiaoService);
			let srvcSpy=sinon.spy(camiaoServiceInstance,"getCamiaoByMatricula");
		// Act
		
		await ctrl.createCamiao(<Request>req, <Response>res, <NextFunction>next);

		let camiaoBuscado=ctrl.getCamiaoByMatricula(<Request>req,<Response>res,<NextFunction>next);
		console.log(matricula2+" matricukla")
		
		// Assert

		sinon.assert.calledOnce(srvcSpy);
		sinon.assert.calledWith("AA-00-00",srvcSpy);
		
		/*sinon.assert.match(res.json,{"id":"123",
		"matricula": req.body.matricula,
		"caracteristica":req.body.caracteristica,
		"tara":req.body.tara,
		"capacidade":req.body.capacidade,
		"carga":req.body.carga,
		"autonomia":req.body.autonomia,
		"tempo":req.body.tempo})
		
	});

	it('camiaoController + camiaoService integration test using camiaoRepoistory and camiao stubs', async function () {	
		// Arrange	
        let body = { "matricula":'AA-00-01',
		"caracteristica":"eTruck01",
		 "tara":  7500 ,
		 "capacidade":4300 ,
		 "carga":80 ,
		 "autonomia":100 ,
		 "tempo":60   };
        let req: Partial<Request> = {};
		req.body = body;
			
        let res: Partial<Response> = {
			status:sinon.spy(),
			
        };
		let next: Partial<NextFunction> = () => {};
		
		sinon.stub(Camiao, "create").returns(Result.ok({"id":"123",
		"matricula": req.body.matricula,
		"caracteristica":req.body.caracteristica,
		"tara":req.body.tara,
		"capacidade":req.body.capacidade,
		"carga":req.body.carga,
		"autonomia":req.body.autonomia,
		"tempo":req.body.tempo}));

		let camiaoRepoInstance = Container.get("CamiaoRepo");
		sinon.stub(camiaoRepoInstance, "create").returns(new Promise<Camiao>((resolve, reject) => {
			resolve(Camiao.create({"id":"123",
			"matricula": req.body.matricula,
			"caracteristica":req.body.caracteristica,
			"tara":req.body.tara,
			"capacidade":req.body.capacidade,
			"carga":req.body.carga,
			"autonomia":req.body.autonomia,
			"tempo":req.body.tempo}).getValue())
		}));

		let camiaoServiceInstance = Container.get("CamiaoService");

		const ctrl = new CamiaoController(camiaoServiceInstance as ICamiaoService);
		let serviceSpy= sinon.spy(camiaoServiceInstance,"createCamiao");

		// Act
		let  camiaoCriado =await ctrl.createCamiao(<Request>req, <Response>res, <NextFunction>next);

		// Assert
		
		
		
		sinon.assert.calledOnce(serviceSpy);
		sinon.assert.calledWith(serviceSpy,sinon.match({
		"matricula": req.body.matricula,
		"caracteristica":req.body.caracteristica,
		"tara":req.body.tara,
		"capacidade":req.body.capacidade,
		"carga":req.body.carga,
		"autonomia":req.body.autonomia,
		"tempo":req.body.tempo}))
		
		 /*sinon.assert.calledWith(res.json, sinon.match({"id":"123",
		"matricula": req.body.matricula,
		"caracteristica":req.body.caracteristica,
		"tara":req.body.tara,
		"capacidade":req.body.capacidade,
		"carga":req.body.carga,
		"autonomia":req.body.autonomia,
		"tempo":req.body.tempo}));
	});
})
*/
