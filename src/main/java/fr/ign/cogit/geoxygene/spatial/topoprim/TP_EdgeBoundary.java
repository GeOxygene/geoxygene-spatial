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

package fr.ign.cogit.geoxygene.spatial.topoprim;

/**
 * Frontière d'un brin topologique. Contient 2 référence vers des
 * TP_DirectedNode, le startNode est orienté négativement, le endNode
 * positivement. Comme TP_Expression, le TP_EdgeBoundary se présente comme ceci
 * : Edge.boundary()=+endNode-startNode.
 * 
 * @author Thierry Badard & Arnaud Braun
 * @version 1.0
 * 
 */

public class TP_EdgeBoundary extends TP_PrimitiveBoundary {

  /** Constructeur par défaut. */
  public TP_EdgeBoundary() {
  }

  /** Constructeur à partir d'un noeud initial et d'un noeud final */
  public TP_EdgeBoundary(TP_DirectedNode theStartNode,
      TP_DirectedNode theEndNode) {
    this.term.add(theEndNode);
    this.term.add(theStartNode);
    this.endNode = theEndNode;
    this.startNode = theStartNode;
  }

  /** Noeud initial. */
  protected TP_DirectedNode startNode;

  /** Renvoie le noeud initial. */
  public TP_DirectedNode getStartnode() {
    return this.startNode;
  }

  /** Noeud final. */
  protected TP_DirectedNode endNode;

  /** Renvoie le noeud final. */
  public TP_DirectedNode getEndnode() {
    return this.endNode;
  }

}
