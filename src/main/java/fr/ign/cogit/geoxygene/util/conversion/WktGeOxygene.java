/* Generated By:JavaCC: Do not edit this line. WktGeOxygene.java */
package fr.ign.cogit.geoxygene.util.conversion;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IDirectPosition;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.ILineString;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IPolygon;
import fr.ign.cogit.geoxygene.api.spatial.geomaggr.IAggregate;
import fr.ign.cogit.geoxygene.api.spatial.geomaggr.IMultiCurve;
import fr.ign.cogit.geoxygene.api.spatial.geomaggr.IMultiPoint;
import fr.ign.cogit.geoxygene.api.spatial.geomaggr.IMultiSurface;
import fr.ign.cogit.geoxygene.api.spatial.geomprim.IPoint;
import fr.ign.cogit.geoxygene.api.spatial.geomprim.IRing;
import fr.ign.cogit.geoxygene.api.spatial.geomroot.IGeometry;
import fr.ign.cogit.geoxygene.spatial.coordgeom.DirectPosition;
import fr.ign.cogit.geoxygene.spatial.coordgeom.GM_LineString;
import fr.ign.cogit.geoxygene.spatial.coordgeom.GM_Polygon;
import fr.ign.cogit.geoxygene.spatial.geomaggr.GM_Aggregate;
import fr.ign.cogit.geoxygene.spatial.geomaggr.GM_MultiCurve;
import fr.ign.cogit.geoxygene.spatial.geomaggr.GM_MultiPoint;
import fr.ign.cogit.geoxygene.spatial.geomaggr.GM_MultiSurface;
import fr.ign.cogit.geoxygene.spatial.geomprim.GM_Point;
import fr.ign.cogit.geoxygene.spatial.geomprim.GM_Ring;


@SuppressWarnings("unchecked")
public class WktGeOxygene implements WktGeOxygeneConstants {
    static class EndOfFile extends Exception {private static final long serialVersionUID = 1L;}
    static class EmptyLine extends Exception {private static final long serialVersionUID = 1L;}

    /*-----------------------------------------------------*/
    /*- Create Wkt object(s) from IGeometry ---------------*/
    /*-----------------------------------------------------*/

    /*- IAggregate --------------------------------------*/

    static String geometryCollectionTaggedText(IAggregate aggregate)
    {
        StringBuffer result=new StringBuffer();
        result.append("GEOMETRYCOLLECTION ");
        if (IsEmptyUtil.isEmpty(aggregate)) result.append("EMPTY");
        else {
                result.append("(");
                for (int i=0; i<aggregate.size(); i++) {
                    if (i!=0)
                        result.append(", ");
                    result.append(makeWkt(aggregate.get(i)));
                }
                result.append(")");
        }
        return result.toString();
    }

    /*- IMultiPoint -------------------------------------*/

    static String multiPointTaggedText(IMultiPoint multiPoint)
    {
        IPoint point;
        StringBuffer result=new StringBuffer();
        result.append("MULTIPOINT ");
        if (IsEmptyUtil.isEmpty(multiPoint)) result.append("EMPTY");
        else {
                result.append("(");
                for (int i=0; i<multiPoint.size(); i++) {
                    point=multiPoint.get(i);
                    if (i!=0)
                        result.append(", ");
                    result.append(point(point));
                }
                result.append(")");
        }
        return result.toString();
    }

    /*- GM_MultiCurve -------------------------------------*/

    static String multiLineStringTaggedText(IMultiCurve multiCurve)
    {
        ILineString lineString;
        StringBuffer result=new StringBuffer();
        result.append("MULTILINESTRING ");
        if (IsEmptyUtil.isEmpty(multiCurve)) result.append("EMPTY");
        else {
                result.append("(");
                for (int i=0; i<multiCurve.size(); i++) {
                    lineString=(ILineString)multiCurve.get(i);
                    if (i!=0)
                        result.append(", ");
                    result.append(lineStringText(lineString));
                }
                result.append(")");
        }
        return result.toString();
    }

    /*- IMultiSurface -----------------------------------*/

        static String multiPolygon(IMultiSurface multiSurface)
        {
        StringBuffer result=new StringBuffer();
        for (int i=0; i<multiSurface.size(); i++) {
                        IGeometry surface;
            surface=multiSurface.get(i);
            if (i!=0)
                result.append(", ");
            if (surface instanceof IPolygon)
                result.append(polygonText((IPolygon)surface));
                else if (surface instanceof IMultiSurface)
                        result.append(multiPolygon((IMultiSurface)surface));
        }
        return result.toString();
        }

        static String multiPolygonText(IMultiSurface multiSurface)
        {
        StringBuffer result=new StringBuffer();
        result.append("(");
                result.append(multiPolygon(multiSurface));
        result.append(")");
                return result.toString();
        }

    static String multiPolygonTaggedText(IMultiSurface multiSurface)
    {
        StringBuffer result=new StringBuffer();
        result.append("MULTIPOLYGON ");
        if (IsEmptyUtil.isEmpty(multiSurface)) result.append("EMPTY");
        else {
                        result.append(multiPolygonText(multiSurface));
            }
        return result.toString();
    }

    /*- ILineString -------------------------------------*/

    static String lineStringText(ILineString lineString)
    {
        IPoint point;
        StringBuffer result=new StringBuffer();
        result.append("(");
        for (int i=0; i<lineString.sizeControlPoint(); i++) {
            point=new GM_Point(lineString.getControlPoint(i));
            if (i!=0)
                result.append(", ");
            result.append(point(point));
        }
        result.append(")");
        return result.toString();
    }

    static String lineStringTaggedText(ILineString lineString)
    {
        StringBuffer result=new StringBuffer();
        result.append("LINESTRING ");
        if (IsEmptyUtil.isEmpty(lineString)) result.append("EMPTY");
        else result.append(lineStringText(lineString));

        return result.toString();
    }

    /*- IPolygon ----------------------------------------*/

    static String polygonText(IPolygon polygon)
    {
        ILineString lineString;

        StringBuffer result=new StringBuffer();
        result.append("(");

        lineString=polygon.exteriorLineString();
        result.append(lineStringText(lineString));

        for (int i=0; i<polygon.sizeInterior(); i++) {
            lineString=polygon.interiorLineString(i);
            result.append(", ");
            result.append(lineStringText(lineString));
        }
        result.append(")");
        return result.toString();
    }

    static String polygonTaggedText(IPolygon polygon)
    {
        StringBuffer result=new StringBuffer();
        result.append("POLYGON ");
        if (IsEmptyUtil.isEmpty(polygon)) result.append("EMPTY");
        else result.append(polygonText(polygon));
        return result.toString();
    }

    /*- IPoint ------------------------------------------*/

//TODO déterminer la dimension de la géométrie attendue par postgis
    static String point(IPoint point)
    {
        IDirectPosition position=point.getPosition();
        StringBuffer result=new StringBuffer();
        result.append(position.getX());
        result.append(" ");
        result.append(position.getY());
                if (!Double.isNaN(position.getZ())) {
                        result.append(" ");
                        result.append(position.getZ());
                }
        return result.toString();
    }

    static String pointText(IPoint point)
    {
        StringBuffer result=new StringBuffer();
        result.append("(");
        result.append(point(point));
        result.append(")");
        return result.toString();
    }

    static String pointTaggedText(IPoint point)
    {
        StringBuffer result=new StringBuffer();
        result.append("POINT ");
        if (IsEmptyUtil.isEmpty(point)) result.append("EMPTY");
        else result.append(pointText(point));
        return result.toString();
    }

    /*- IRing -----------------------------------------*/

    private static String ringTaggedText(IRing ring) {
                StringBuffer result=new StringBuffer();
                result.append("RING ");
                if (IsEmptyUtil.isEmpty(ring)) result.append("EMPTY");
                else result.append(ringText(ring));
                return result.toString();
        }

        private static Object ringText(IRing ring) {
                IPoint point;
                StringBuffer result=new StringBuffer();
                result.append("(");
                for (int i=0; i<ring.coord().size(); i++) {
                        point=new GM_Point(ring.coord().get(i));
                        if (i!=0)
                                result.append(", ");
                        result.append(point(point));
                }
                result.append(")");
                return result.toString();
        }

    /*- IGeometry -----------------------------------------*/

        public static String makeWkt(IGeometry object)
        {
                String result="POINT EMPTY";
                if (object instanceof IPoint)
                        result=pointTaggedText((IPoint)object);
                else if (object instanceof IMultiSurface)
                        result=multiPolygonTaggedText((IMultiSurface)object);
                else if (object instanceof IMultiCurve)
                        result=multiLineStringTaggedText((IMultiCurve)object);
                else if (object instanceof IMultiPoint)
                        result=multiPointTaggedText((IMultiPoint)object);
                else if (object instanceof IPolygon)
                        result=polygonTaggedText((IPolygon)object);
                else if (object instanceof ILineString)
                        result=lineStringTaggedText((ILineString)object);
                else if (object instanceof IAggregate)
                        result=geometryCollectionTaggedText((IAggregate)object);
                else if (object instanceof IRing)
                        result=ringTaggedText((IRing)object);
                return result;
        }

    public static String makeWkt(List<?> geomList)
    {
        StringBuffer result=new StringBuffer();
        Iterator<?> i=geomList.iterator();
        while (i.hasNext()) {
                IGeometry geom=(IGeometry)i.next();
                String wkt=makeWkt(geom);
                result.append(wkt);
                result.append('\u005cn');
        }
        return result.toString();
    }

    /*- Read from stream ----------------------------------*/

    public static IGeometry readGeOxygeneFromWkt(BufferedReader in)
    throws IOException,ParseException
    {
        String wkt=in.readLine();
        return makeGeOxygene(wkt);
    }

    public static IGeometry readGeOxygeneFromWkt(InputStream in)
    throws IOException,ParseException
    {
        return readGeOxygeneFromWkt(new BufferedReader(new InputStreamReader(in)));
    }

    public static IGeometry readGeOxygeneFromWkt(String path)
    throws FileNotFoundException,IOException,ParseException
    {
        return readGeOxygeneFromWkt(new FileInputStream(path));
    }

    /*- Write to stream -----------------------------------*/

    public static void writeWkt(String path, boolean append, IGeometry geom)
    throws IOException
    {
                writeWkt(new FileOutputStream(path, append), geom);
    }

    public static void writeWkt(String path, IGeometry geom)
    throws IOException
    {
                writeWkt(new FileOutputStream(path), geom);
    }

    public static void writeWkt(OutputStream out, IGeometry geom)
    throws IOException
    {
        new PrintStream(out).println(makeWkt(geom));
    }

    public static void writeWkt(OutputStream out, List<?> geomList)
    throws IOException
    {
        Iterator<?> i=geomList.iterator();
        while (i.hasNext()) {
            IGeometry geom=(IGeometry)i.next();
            writeWkt(out,geom);
        }
    }

    /*-----------------------------------------------------*/
    /*- Create IGeometry from Wkt object(s) ---------------*/
    /*-----------------------------------------------------*/

    public static List<?> makeGeOxygeneList(String inStrArray[])
        throws ParseException
    {
        ArrayList<IGeometry> list=new ArrayList<IGeometry>();
        for (int i=0; i<inStrArray.length; i++) {
            list.add(makeGeOxygene(inStrArray[i]));
        }
        return list;
    }

    static IGeometry makeGeOxygene(InputStream in)
    throws ParseException
    {
        WktGeOxygene parser=new WktGeOxygene(in);
        IGeometry geom=null;

        try {
            geom=parser.parseOneLine();
        }
        catch (EndOfFile x) {}
        catch (EmptyLine x) {}

        return geom;
    }

    public static List<?> makeGeOxygeneList(File file)
    throws Exception
    {
        return makeGeOxygeneList(new FileInputStream(file));
    }

    public static List<?> makeGeOxygeneList(String wkt)
    throws Exception
    {
        InputStream in=new ByteArrayInputStream(wkt.getBytes());
        return makeGeOxygeneList(in);
    }

    public static List<?> makeGeOxygeneList(InputStream in)
    throws ParseException
    {
        ArrayList<IGeometry> list=new ArrayList<IGeometry>();
        WktGeOxygene parser=new WktGeOxygene(in);

        while (true) {
            try {
                IGeometry geom=parser.parseOneLine();
                list.add(geom);
                        } catch (EndOfFile x) {
                break;
            } catch (EmptyLine x) {}
        }
        return list;
    }

    public static IGeometry makeGeOxygene(String inStr)
    throws ParseException
    {
        InputStream in=new ByteArrayInputStream(inStr.getBytes());
        return makeGeOxygene(in);
    }

  final public IDirectPosition point() throws ParseException {
    IDirectPosition p;
    Token xy;
    xy = jj_consume_token(POINT);
        StringTokenizer tkz=new StringTokenizer(xy.image);
        String xStr=tkz.nextToken();
        String yStr=tkz.nextToken();
        if (tkz.hasMoreTokens()) {
                // récupération de la 3eme coordonnée si elle existe
                String zStr=tkz.nextToken();
                if (tkz.hasMoreTokens()) {
                        // si il y en a une 4ème, alors on est en XYZM et on prend Z
                        // sinon, on est en XYM et on ignore la mesure car elle n'est pas gérée par le type DirectPosition
                        p=new DirectPosition(Double.parseDouble(xStr), Double.parseDouble(yStr), Double.parseDouble(zStr));
                                {if (true) return p;}
                        }
                p=new DirectPosition(Double.parseDouble(xStr), Double.parseDouble(yStr), Double.parseDouble(zStr));
            {if (true) return p;}
                }
        p=new DirectPosition(
            Double.parseDouble(xStr), Double.parseDouble(yStr));
        try {{if (true) return p;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IDirectPosition pointText() throws ParseException {
 IDirectPosition p=new DirectPosition();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 9:
      jj_consume_token(9);
      p = point();
      jj_consume_token(10);
      break;
    case 11:
      jj_consume_token(11);
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     try {{if (true) return p;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public ILineString linestringText() throws ParseException {
    List<IDirectPosition> points = new ArrayList<IDirectPosition>();
    IDirectPosition p;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 9:
      jj_consume_token(9);
      p = point();
               points.add(p);
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 12:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_1;
        }
        jj_consume_token(12);
        p = point();
                    points.add(p);
      }
      jj_consume_token(10);
      break;
    case 11:
      jj_consume_token(11);
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     try {{if (true) return new GM_LineString(points);}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IPolygon polygonText() throws ParseException {
    IPolygon polygon=new GM_Polygon();
    ILineString lineString;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 9:
      jj_consume_token(9);
      lineString = linestringText();
                polygon=new GM_Polygon(lineString);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 12:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_2;
        }
        jj_consume_token(12);
        lineString = linestringText();
            polygon.addInterior(new GM_Ring(lineString));
      }
      jj_consume_token(10);
      break;
    case 11:
      jj_consume_token(11);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     try {{if (true) return polygon;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IMultiPoint multipointText() throws ParseException {
    IMultiPoint multiPoint=new GM_MultiPoint();
    IDirectPosition p;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 9:
      jj_consume_token(9);
      p = point();
               multiPoint.add(new GM_Point(p));
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 12:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_3;
        }
        jj_consume_token(12);
        p = point();
                    multiPoint.add(new GM_Point(p));
      }
      jj_consume_token(10);
      break;
    case 11:
      jj_consume_token(11);
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     try {{if (true) return multiPoint;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IMultiCurve multilinestringText() throws ParseException {
    IMultiCurve multiLineString=new GM_MultiCurve();
    ILineString lineString;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 9:
      jj_consume_token(9);
      lineString = linestringText();
                                 multiLineString.add(lineString);
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 12:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_4;
        }
        jj_consume_token(12);
        lineString = linestringText();
         multiLineString.add(lineString);
      }
      jj_consume_token(10);
      break;
    case 11:
      jj_consume_token(11);
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     try {{if (true) return multiLineString;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IMultiSurface multipolygonText() throws ParseException {
    IMultiSurface multiPolygon=new GM_MultiSurface();
    IPolygon polygon;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 9:
      jj_consume_token(9);
      polygon = polygonText();
                           multiPolygon.add(polygon);
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 12:
          ;
          break;
        default:
          jj_la1[9] = jj_gen;
          break label_5;
        }
        jj_consume_token(12);
        polygon = polygonText();
                                multiPolygon.add(polygon);
      }
      jj_consume_token(10);
      break;
    case 11:
      jj_consume_token(11);
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     try {{if (true) return multiPolygon;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IAggregate geometrycollectionText() throws ParseException {
    IAggregate geometryCollection=new GM_Aggregate();
    IGeometry geometry;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 9:
      jj_consume_token(9);
      geometry = geometryTaggedText();
                                   geometryCollection.add(geometry);
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 12:
          ;
          break;
        default:
          jj_la1[11] = jj_gen;
          break label_6;
        }
        jj_consume_token(12);
        geometry = geometryTaggedText();
         geometryCollection.add(geometry);
      }
      jj_consume_token(10);
      break;
    case 11:
      jj_consume_token(11);
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     try {{if (true) return geometryCollection;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IPoint pointTaggedText() throws ParseException {
 IDirectPosition p;
    jj_consume_token(13);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 14:
    case 15:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 14:
        jj_consume_token(14);
        break;
      case 15:
        jj_consume_token(15);
        break;
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[14] = jj_gen;
      ;
    }
    p = pointText();
     try {{if (true) return new GM_Point(p);}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IMultiPoint multipointTaggedText() throws ParseException {
 IMultiPoint mp;
    jj_consume_token(16);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 14:
    case 15:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 14:
        jj_consume_token(14);
        break;
      case 15:
        jj_consume_token(15);
        break;
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[16] = jj_gen;
      ;
    }
    mp = multipointText();
     try {{if (true) return mp;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public ILineString linestringTaggedText() throws ParseException {
 ILineString lineString;
    jj_consume_token(17);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 14:
    case 15:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 14:
        jj_consume_token(14);
        break;
      case 15:
        jj_consume_token(15);
        break;
      default:
        jj_la1[17] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[18] = jj_gen;
      ;
    }
    lineString = linestringText();
     try {{if (true) return lineString;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IMultiCurve multilinestringTaggedText() throws ParseException {
 IMultiCurve multiLineString;
    jj_consume_token(18);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 14:
    case 15:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 14:
        jj_consume_token(14);
        break;
      case 15:
        jj_consume_token(15);
        break;
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[20] = jj_gen;
      ;
    }
    multiLineString = multilinestringText();
     try {{if (true) return multiLineString;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IPolygon polygonTaggedText() throws ParseException {
 IPolygon poly;
    jj_consume_token(19);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 14:
    case 15:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 14:
        jj_consume_token(14);
        break;
      case 15:
        jj_consume_token(15);
        break;
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[22] = jj_gen;
      ;
    }
    poly = polygonText();
     try {{if (true) return poly;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IMultiSurface multipolygonTaggedText() throws ParseException {
 IMultiSurface mp;
    jj_consume_token(20);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 14:
    case 15:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 14:
        jj_consume_token(14);
        break;
      case 15:
        jj_consume_token(15);
        break;
      default:
        jj_la1[23] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[24] = jj_gen;
      ;
    }
    mp = multipolygonText();
     try {{if (true) return mp;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IAggregate geometrycollectionTaggedText() throws ParseException {
 IAggregate o;
    jj_consume_token(21);
    o = geometrycollectionText();
     try {{if (true) return o;}} catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public int sridText() throws ParseException {
 Token srid;
    jj_consume_token(22);
    srid = jj_consume_token(CONSTANT);
    jj_consume_token(23);
        StringTokenizer tkz=new StringTokenizer(srid.image);
            String str=tkz.nextToken();
                {if (true) return Integer.parseInt(str);}
    throw new Error("Missing return statement in function");
  }

  final public IGeometry geometryTaggedText() throws ParseException {
IGeometry o;
int srid = -1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 22:
      srid = sridText();
      break;
    default:
      jj_la1[25] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 13:
      o = pointTaggedText();
      break;
    case 17:
      o = linestringTaggedText();
      break;
    case 19:
      o = polygonTaggedText();
      break;
    case 16:
      o = multipointTaggedText();
      break;
    case 18:
      o = multilinestringTaggedText();
      break;
    case 20:
      o = multipolygonTaggedText();
      break;
    case 21:
      o = geometrycollectionTaggedText();
      break;
    default:
      jj_la1[26] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     try {
        if (srid != -1) { o.setCRS(srid); }
        {if (true) return o;}
    } catch (Exception e) {System.out.println(e);}
    throw new Error("Missing return statement in function");
  }

  final public IGeometry parseOneLine() throws ParseException, EmptyLine, EndOfFile {
 IGeometry o;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 13:
    case 16:
    case 17:
    case 18:
    case 19:
    case 20:
    case 21:
    case 22:
      o = geometryTaggedText();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOL:
        jj_consume_token(EOL);
        break;
      case 0:
        jj_consume_token(0);
        break;
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                                           try {{if (true) return o;}} catch (Exception e) {System.out.println(e);}
      break;
    case EOL:
      jj_consume_token(EOL);
             try {{if (true) return null;}} catch (Exception e) {System.out.println(e);}
      break;
    case 0:
      jj_consume_token(0);
             try {{if (true) return null;}} catch (Exception e) {System.out.println(e);}
      break;
    default:
      jj_la1[28] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public WktGeOxygeneTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[29];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xa00,0x1000,0xa00,0x1000,0xa00,0x1000,0xa00,0x1000,0xa00,0x1000,0xa00,0x1000,0xa00,0xc000,0xc000,0xc000,0xc000,0xc000,0xc000,0xc000,0xc000,0xc000,0xc000,0xc000,0xc000,0x400000,0x3f2000,0x41,0x7f2041,};
   }

  /** Constructor with InputStream. */
  public WktGeOxygene(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public WktGeOxygene(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new WktGeOxygeneTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public WktGeOxygene(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new WktGeOxygeneTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public WktGeOxygene(WktGeOxygeneTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(WktGeOxygeneTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[24];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 29; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 24; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}