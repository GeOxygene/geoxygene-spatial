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

package fr.ign.cogit.geoxygene.util.conversion;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AwtAggregate implements AwtShape {
  private List<AwtShape> awtShapeList;
  private GeneralPath path;

  public AwtAggregate() {
    this.awtShapeList = new ArrayList<AwtShape>();
    this.path = new GeneralPath();
  }

  public GeneralPath getPath() {
    return this.path;
  }

  public void add(AwtShape shape) {
    this.awtShapeList.add(shape);
    this.path.append(shape.getBounds(), false);
  }

  @Override
  public void draw(Graphics2D g) {
    Iterator<AwtShape> i = this.awtShapeList.iterator();
    while (i.hasNext()) {
      AwtShape shape = i.next();
      shape.draw(g);
    }
  }

  @Override
  public Rectangle2D getBounds() {
    return this.path.getBounds();
  }
}
