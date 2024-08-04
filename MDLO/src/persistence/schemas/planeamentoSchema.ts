/* eslint-disable prettier/prettier */
import { IPlaneamentoPersistence } from '../../dataschema/IPlaneamentoPersistence';
import mongoose from 'mongoose';

const PlaneamentoSchema = new mongoose.Schema(
  {
    domainId: {
      type: String,
      unique: true
    },

    dia:{
      type: Number,
      required: [true,'Por favor coloque o dia'],
      unique:true
    },

    camiao: {
      type: String,
      required: [true, 'Por favor coloque o camião'],
      index: true,
    },

    curso: {
      type: [String],
      required: [true, 'Por favor coloque o curso'],
      index: true,
    },
    

    
  },
  { timestamps: true },
);

export default mongoose.model<IPlaneamentoPersistence & mongoose.Document>('Planeamento', PlaneamentoSchema);
