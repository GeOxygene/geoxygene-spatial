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

import fr.ign.cogit.geoxygene.api.spatial.coordgeom.ISurfacePatch;
import fr.ign.cogit.geoxygene.spatial.geomprim.GM_Surface;

/**
 * Portion homogène d'une GM_Surface.
 * 
 * <P>
 * Modification de la norme : cette classe hérite de GM_Surface. Du coup on a
 * fait sauter le lien d'implémentation de GM_GenericSurface. Un GM_SurfacePatch
 * sera une GM_Surface composée d'un et d'un seul segment qui sera lui-même. Les
 * méthodes addSegment, removeSegment, etc... seront interdites.
 * 
 * @author Thierry Badard & Arnaud Braun
 * @version 1.0
 * 
 */

abstract public class GM_SurfacePatch extends GM_Surface
/* implements GM_GenericSurface */implements ISurfacePatch {

  /**
   * Mécanisme d'interpolation, selon une liste de codes. La liste de codes est
   * la suivante : {none, planar, spherical, elliptical, conic, tin,
   * parametricCurve, polynomialSpline, rationalSpline, triangulatedSpline}.
   */
  protected String interpolation;

  @Override
  public String getInterpolation() {
    return this.interpolation;
  }

  /**
   * Continuité entre self et ses voisins qui partagent une frontière commune.
   * Vaut 0 par défaut.
   */
  protected int numDerivativesOnBoundary = 0;

  @Override
  public int getNumDerivativesOnBoundary() {
    return this.numDerivativesOnBoundary;
  }

  /**
   * Renvoie un GM_SurfacePatch de sens oppose. Methode abstraite implementee
   * dans les sous-classes
   */
  @Override
  abstract public ISurfacePatch reverse();

  @Override
  public ISurfacePatch getPatch(int i) {
    if (i != 0) {
      System.out
          .println("Recherche d'un segment avec i<>0 alors qu'un GM_SurfacePatch ne contient qu'un patch qui est lui-meme."); //$NON-NLS-1$
      return null;
    }
    return this.patch.get(i);
  }

  @Override
  public void setPatch(int i, ISurfacePatch value) {
    System.out
        .println("Méthode inapplicable sur un GM_SurfacePatch. La méthode ne fait rien."); //$NON-NLS-1$
  }

  @Override
  public void addPatch(ISurfacePatch value) {
    System.out
        .println("Méthode inapplicable sur un GM_SurfacePatch. La méthode ne fait rien."); //$NON-NLS-1$
  }

  @Override
  public void addPatch(int i, ISurfacePatch value) {
    System.out
        .println("Méthode inapplicable sur un GM_SurfacePatch. La méthode ne fait rien."); //$NON-NLS-1$
  }

  @Override
  public void removePatch(ISurfacePatch value) {
    System.out
        .println("Méthode inapplicable sur un GM_SurfacePatch. La méthode ne fait rien."); //$NON-NLS-1$
  }

  @Override
  public void removePatch(int i) {
    System.out
        .println("Méthode inapplicable sur un GM_SurfacePatch. La méthode ne fait rien."); //$NON-NLS-1$
  }
}
