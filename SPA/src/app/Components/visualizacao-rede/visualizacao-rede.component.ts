import { Component, OnInit } from '@angular/core';
import * as THREE from 'three';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls'
import TextSprite from '@seregpie/three.text-sprite';
import { Scene } from 'three';
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import { Posicao } from 'src/app/Model/posicao';
import { ArmazemService } from 'src/app/Services/armazem.service';
import { Armazem } from 'src/app/Model/armazem';
import { ArmazemData } from 'src/app/Model/armazemData';
import { ArmazemDataService } from 'src/app/Services/armazemData.service';
import { of } from 'rxjs';
import {Title} from "@angular/platform-browser";
@Component({
  selector: 'visualizacao-rede',
  templateUrl: './visualizacao-rede.component.html',
  styleUrls: ['./visualizacao-rede.component.css'],
})
export class VisualizacaoRedeComponent implements OnInit {

  armazens:Armazem[] = [];
  armazensData:ArmazemData[] = [];
  xBorder : number = 50;
  constructor(private armazemService : ArmazemService, private armazemDataService: ArmazemDataService,private titleService:Title) {
    this.titleService.setTitle("Visualização Rede Viária");
  }

  ngOnInit(): void {
    const req = this.armazemService.getArmazens();
    const req2 = this.armazemDataService.getArmazemData();


    req.subscribe(x=>{

      this.armazens = x;

    } )
    req2.subscribe(y=>{
      this.armazensData = y;
      this.createThreeJsBox();
    } )


  }

  createThreeJsBox(): void {


    const canvas = document.getElementById('canvas-box');

    const scene = new THREE.Scene();


    const ambientLight = new THREE.AmbientLight(0xffffff, 0.5);
    scene.add(ambientLight);

    const pointLight = new THREE.PointLight(0xffffff, 0.5);
    pointLight.position.x = 2;
    pointLight.position.y = 2;
    pointLight.position.z = 2;
    scene.add(pointLight);



    // Shared variables
    const K_LIGACAO = 1.1;
    const INFINETESIMO = 0.03;
    const K_CIRCULO = 2.1;
    let wi = 1.5;
    let RAIO = K_CIRCULO * wi / 2.0;

    let array: Posicao[] = [];

    this.armazensData.forEach(element => {
    });


    this.armazens.forEach(element => {
      let posicao : Posicao = {
        id: element.id,
        designacao: element.designacao,
        longitude : element.longitude,
        latitude : element.latitude,
        altitude : element.altitude,
        armazensAdjacentes : [],
        url : []
      }
      array.push(posicao);
    });

    let index = this.treatId(this.armazensData[0].armazem);
    let armazemAdjacenteIndex;
    this.armazensData.forEach(element => {
        if(element.Id != index) {
          index = this.treatId(element.armazem);
        }
        armazemAdjacenteIndex = this.treatId(element.armazemAdjacente);
        array[Number(index) - 1].armazensAdjacentes.push(array[Number(armazemAdjacenteIndex) -1]) ;
        array[Number(index) - 1].url.push(element.urlArmazem);
    });




    let positionX : number;
    let positionY : number;
    let positionZ : number;

    let positionX2 : number;
    let positionY2 : number;
    let positionZ2 : number;


    let points, geometry, mesh;

    const normals = new Float32Array([
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0
    ]);

    const indexes = [
        0, 2, 1,
        1, 2, 3,
        2, 6, 3,
        3, 6, 7,
        6, 4, 7,
        7, 4, 5
    ];

    const camiaoGeometry = new THREE.BoxGeometry(1,1,1);
    const camiaoMaterial = new THREE.MeshBasicMaterial({color:0x00ff00});
    const camiaoCube = new THREE.Mesh(camiaoGeometry,camiaoMaterial);
    scene.add(camiaoCube);
    let xCa = ((50 - (-50)) / (8.7613 - 8.2451)) * (41.1844 - 8.2451 ) + (-50);
    let yCa = ((50 - (-50)) / (42.1115 - 40.8387)) * (8.6963 - 40.8387 ) + (-50);
    let zCa = ((50 - 0) / (800 - 0)) * (350 - 0 ) + (0);
    camiaoCube.position.x=xCa;
    camiaoCube.position.y=yCa;
    camiaoCube.position.z=zCa;






    array.forEach(armazem => {
    let coordenadas = this.calculateGeographicToCarthesianCoordinates(armazem.longitude, armazem.latitude, armazem.altitude);
    positionX = coordenadas[0];
    positionY = coordenadas[2];
    positionZ = coordenadas[1];
    this.importArmazem3D(positionX,positionY,positionZ,scene,armazem.url[0]);

    let instance = new TextSprite({
      alignment: 'left',
      color: '#24ff00',
      fontFamily: '"Times New Roman", Times, serif',
      fontSize: 4,
      fontStyle: 'italic',
      text: armazem.designacao,
    });

    instance.position.x = positionX;
    instance.position.y = positionY + 5;
    instance.position.z = positionZ;

    scene.add(instance);

      const geometryNode = new THREE.CircleGeometry( RAIO, 32 );
      const materialNode = new THREE.MeshBasicMaterial( { color: 0x3C6FC8 } );
      const node = new THREE.Mesh( geometryNode, materialNode );
      node.rotation.x = Math.PI / 2;
      node.position.x = positionX;
      node.position.y = positionY;
      node.position.z = positionZ;
      node.material.side = THREE.DoubleSide;
      scene.add( node );

        armazem.armazensAdjacentes.forEach(armazensAdjacentes => {
          let coordenadas2 = this.calculateGeographicToCarthesianCoordinates(armazensAdjacentes.longitude, armazensAdjacentes.latitude, armazensAdjacentes.altitude);
          positionX2 = coordenadas2[0];
          positionY2 = coordenadas2[2];
          positionZ2 = coordenadas2[1];
          const compOri = RAIO * 1.5;
          const compDest = RAIO * 1.5;
          const largura = 0.6;


                //busca os vertices a frente do centro
                const direction = new THREE.Vector3(
                  positionX2 - positionX,
                    0,
                    positionZ2 - positionZ
                ).clampLength(0, 1);


                //busca os vertices ao lado do centro(armazÃƒÂ©m)
                const crossDirection = new THREE.Vector3(
                    direction.x,
                    0,
                    direction.z
                ).cross(new THREE.Vector3(0, 1, 0));



                points = [
                    new THREE.Vector3(positionX + crossDirection.x * largura, positionY, positionZ + crossDirection.z * largura),
                    new THREE.Vector3(positionX - crossDirection.x * largura, positionY, positionZ - crossDirection.z * largura)
                ];

                points.push(
                    new THREE.Vector3(points[0].x + direction.x * compOri, points[0].y, points[0].z + direction.z * compOri),
                    new THREE.Vector3(points[1].x + direction.x * compOri, points[1].y, points[1].z + direction.z * compOri)
                )

                points.push(
                    new THREE.Vector3(positionX2 + crossDirection.x * largura, positionY2, positionZ2 + crossDirection.z * largura),
                    new THREE.Vector3(positionX2 - crossDirection.x * largura, positionY2, positionZ2 - crossDirection.z * largura)
                )

                points.push(
                    new THREE.Vector3(points[4].x - direction.x * compDest, points[4].y, points[4].z - direction.z * compDest),
                    new THREE.Vector3(points[5].x - direction.x * compDest, points[5].y, points[5].z - direction.z * compDest)
                )

                //juntar pontos
                geometry = new THREE.BufferGeometry().setFromPoints(points);
                geometry.setAttribute('normal', new THREE.BufferAttribute(normals, 3));
                geometry.setIndex(indexes);

                let material = new THREE.MeshBasicMaterial({ color: 0xC45B11, side: THREE.DoubleSide});


                mesh = new THREE.Mesh(geometry, material);

                scene.add(mesh);
        });
  });





    const canvasSizes = {
      width: window.innerWidth,
      height: window.innerHeight,
    };

    const positionXCamera = ((50 - (-50)) / (8.7613 - 8.2451)) * (array[4].longitude - 8.2451 ) + (-50);
    const positionYCamera = ((50 - (-50)) / (42.1115 - 40.8387)) * (array[4].latitude - 40.8387 ) + (-50);
    const positionZCamera = ((50 - 0) / (800 - 0)) * (array[4].altitude - 0 ) + (0);

    const camera = new THREE.PerspectiveCamera(
      75,
      canvasSizes.width / canvasSizes.height,
      0.001,
      1000
    );

    //Change default Axis Y to be Z.
    camera.position.set(0, 90, 0);
    camera.position.z = 30;

    scene.add(camera);



    if (!canvas) {
      return;
    }

    const renderer = new THREE.WebGLRenderer({
      canvas: canvas,
      antialias: true,
    });
    renderer.setSize(canvasSizes.width, canvasSizes.height);

    window.addEventListener('resize', () => {
      canvasSizes.width = window.innerWidth;
      canvasSizes.height = window.innerHeight;

      camera.aspect = canvasSizes.width / canvasSizes.height;
      camera.updateProjectionMatrix();

      renderer.setSize(canvasSizes.width, canvasSizes.height);
      renderer.render(scene, camera);
    });

    window.onresize = function () {
      camera.aspect = window.innerWidth / window.innerHeight;
      camera.updateProjectionMatrix();

      renderer.setSize( window.innerWidth, window.innerHeight );

    };




 const controls = new OrbitControls( camera, renderer.domElement );
			controls.target.set( positionXCamera, positionZCamera, positionYCamera );
			controls.update();
			controls.enablePan = true;
      controls.autoRotate = false;
      controls.enableZoom = true;

    const clock = new THREE.Clock();

    const animateGeometry = () => {
      const elapsedTime = clock.getElapsedTime();

      // Render
      renderer.render(scene, camera);

      // Call tick again on the next frame
      window.requestAnimationFrame(animateGeometry);
    };

    animateGeometry();
  }

  calculateGeographicToCarthesianCoordinates(xGeographic: number, yGeographic: number, zGeographic: number): number[] {
    let values: number[];
    let x = ((50 - (-50)) / (8.7609 - 8.2451)) * (xGeographic - 8.2451 ) + (-50);
    let y = ((50 - (-50)) / (41.3804 - 40.8387)) * (yGeographic - 40.8387 ) + (-50);
    let z = ((50 - 0) / (800 - 0)) * (zGeographic - 0 ) + (0);
    values = [x, y, z];
    return values;
  }

    importArmazem3D (x:number, y:number, z:number, scene : Scene, url :string) {
      const baseSize = 6;
      const baseGeometry = new THREE.PlaneGeometry(baseSize, baseSize);
      const baseMaterial = new THREE.MeshBasicMaterial({ color: 0x3C6FC8 });
      const base = new THREE.Mesh(baseGeometry, baseMaterial);

      const xRotation = (Math.PI/2);

      const baseZ_Offset = 6;
      base.rotation.x = xRotation;
      base.position.x = x;
      base.position.y = y;
      base.position.z = z - baseZ_Offset;
      base.material.side = THREE.DoubleSide;
      scene.add(base);

      const baseLigationSizeX = 1;
      const baseLigationSizeY = 3;
      const baseLigationGeometry = new THREE.PlaneGeometry(baseLigationSizeX, baseLigationSizeY);
      const baseLigationMaterial = new THREE.MeshBasicMaterial({ color: 0x3C6FC8 });
      const ligation = new THREE.Mesh(baseLigationGeometry, baseLigationMaterial);

      const baseLigationZ_Offset = 3;
      ligation.rotation.x = xRotation;
      ligation.position.x = x;
      ligation.position.y = y;
      ligation.position.z = z - baseLigationZ_Offset;
      ligation.material.side = THREE.DoubleSide;
      scene.add(ligation);

      const armazemScale = 0.0004;
      const rootLigationZ_Offset = 6;
      var tamanhoUrl = url?.length;
      if(tamanhoUrl > 0) {
      const loader = new GLTFLoader();
      loader.load(url, function(gltf) {
        const root = gltf.scene;
        root.scale.set(armazemScale, armazemScale, armazemScale);
        root.position.x = x;
        root.position.y = y;
        root.position.z = z - rootLigationZ_Offset;
        scene.add(root);
      },undefined, function (error) {
        console.log(error);
      });
    }
  }



  treatId(id :string){
    let characters = id.split("");
    let index;
    if(characters[0] != '0') {
      index = characters[1] + "" + characters[2]
    }else {
      index = characters[2];
    }
    return index;
  }

}
