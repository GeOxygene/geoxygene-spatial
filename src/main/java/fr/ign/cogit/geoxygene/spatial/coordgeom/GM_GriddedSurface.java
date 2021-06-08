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

package fr.ign.cogit.geoxygene.spatial.coordgeom;

import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IDirectPositionList;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IGriddedSurface;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IPointGrid;

/**
 * NON IMPLEMENTE, A FAIRE. Grille.
 * 
 * @author Thierry Badard & Arnaud Braun
 * @version 1.0
 * 
 */

public class GM_GriddedSurface extends GM_ParametricCurveSurface implements
    IGriddedSurface {

  /**
   * Tableau à deux dimension de points constituant la grille.
   */
  protected IPointGrid controlPoint;

  @Override
  public IPointGrid getControlPoint() {
    return this.controlPoint;
  }

  /**
   * Nombre de lignes dans la grille.
   */
  private int rows;

  protected int getRows() {
    return this.rows;
  }

  /**
   * Nombre de colonnes dans la grille.
   */
  private int columns;

  /**
   * Nombre de colonnes dans la grille. On prend l'hypothèse que toutes les
   * lignes ont le même nombre de colonnes.
   * <p>
   * Number of columns in the grid. We assume that all rows have the same number
   * of columns.
   */
  protected int getColumns() {
    return this.columns;
  }

  @Override
  public IDirectPositionList coord() {
    IDirectPositionList coord = new DirectPositionList();
    int numberOfRows = this.controlPoint.cardRow();
    for (int row = 0; row < numberOfRows; row++) {
      coord.addAll(this.controlPoint.getRow(row));
    }
    return coord;
  }
}
