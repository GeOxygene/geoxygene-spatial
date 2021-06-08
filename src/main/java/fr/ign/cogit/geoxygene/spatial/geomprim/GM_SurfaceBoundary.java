/*
 * This file is part of the GeOxygene project source files.
 * 
 * GeOxygene aims at providing an open framework which implements OGC/ISO
 * specifications for the development and deployment of geographic (GIS)
 * applications. It is a open source contribution of the COGIT laboratory at the
 * Institut Géographique National (the French National Mapping Agency).
 * 
 * See: http://oxygene-project.sourceforge.net
 * 
 * Copyright (C) 2005 Institut Géographique National
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library (see file LICENSE if present); if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */

package fr.ign.cogit.geoxygene.spatial.geomprim;

import java.util.ArrayList;
import java.util.List;

import fr.ign.cogit.geoxygene.api.spatial.geomprim.IRing;
import fr.ign.cogit.geoxygene.api.spatial.geomprim.ISurfaceBoundary;

/**
 * Représente la frontière d'un GM_Surface. Un GM_SurfaceBoundary consiste en un
 * anneau extérieur, et éventuellement des anneaux intérieurs pour représenter
 * les surfaces à trous (le cas de 0 anneeau extérieur est prévu par le modèle
 * mais je n'ai pas compris pourquoi).
 * <P>
 * Pour construitre une GM_SurfaceBoundary, utiliser le constructeur
 * GM_SurfaceBoundary(GM_Ring ring) qui affecte la frontière extérieure, puis
 * s'il le faut ajouter des anneaux intérieurs avec
 * GM_SurfaceBoundary.addInterior(GM_Ring ring).
 * 
 * @author Thierry Badard & Arnaud Braun
 * @version 1.0
 * 
 */

public class GM_SurfaceBoundary extends GM_PrimitiveBoundary implements
    ISurfaceBoundary {
  /** Anneau extérieur. */
  protected IRing exterior;

  @Override
  public IRing getExterior() {
    return this.exterior;
  }

  /** Affecte une valeur à l'anneau extérieur */
  protected void setExterior(GM_Ring value) {
    this.exterior = value;
  }

  @Override
  public int sizeExterior() {
    if (this.exterior == null) {
      return 0;
    }
    return 1;
  }

  /** Anneau(x) intérieur(s) en cas de trou(s) : liste de GM_Ring */
  protected List<IRing> interior;

  @Override
  public List<IRing> getInterior() {
    return this.interior;
  }

  @Override
  public IRing getInterior(int i) {
    return this.interior.get(i);
  }

  @Override
  public void setInterior(int i, IRing value) {
    this.interior.set(i, value);
  }

  @Override
  public void addInterior(IRing value) {
    this.interior.add(value);
  }

  @Override
  public void addInterior(int i, IRing value) {
    this.interior.add(i, value);
  }

  @Override
  public void removeInterior(IRing value) {
    this.interior.remove(value);
  }

  @Override
  public void removeInterior(int i) {
    this.interior.remove(i);
  }

  @Override
  public int sizeInterior() {
    return this.interior.size();
  }

  /** Constructeur par défaut */
  public GM_SurfaceBoundary() {
    this.exterior = null;
    this.interior = new ArrayList<IRing>(0);
  }

  /**
   * Constructeur à partir d'un GM_Ring et d'un seul (pour des surfaces sans
   * trou)
   */
  public GM_SurfaceBoundary(IRing ring) {
    this.exterior = ring;
    this.interior = new ArrayList<IRing>(0);
  }

}
