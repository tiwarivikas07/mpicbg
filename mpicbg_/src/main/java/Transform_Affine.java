import ij.gui.PointRoi;
import mpicbg.ij.InteractiveInvertibleCoordinateTransform;
import mpicbg.models.AffineModel2D;
import mpicbg.models.Point;
import mpicbg.models.PointMatch;

public class Transform_Affine extends InteractiveInvertibleCoordinateTransform< AffineModel2D >
{
	final protected AffineModel2D model = new AffineModel2D();
	
	@Override
	final protected AffineModel2D myModel() { return model; }
	
	@Override
	final protected void setHandles()
	{
//		final float[] x = new float[ 3 ];
//		final float[] y = new float[ 3 ];
		
		final float[] x = new float[]{ imp.getWidth() / 4f, 3f * imp.getWidth() / 4f, imp.getWidth() / 4f };
		final float[] y = new float[]{ imp.getHeight() / 4f, imp.getHeight() / 2f, 3f * imp.getHeight() / 4f };
		
		p = new Point[]{
				new Point( new float[]{ x[ 0 ], y[ 0 ] } ),
				new Point( new float[]{ x[ 1 ], y[ 1 ] } ),
				new Point( new float[]{ x[ 2 ], y[ 2 ] } ) };
		
		q = new Point[]{
				p[ 0 ].clone(),
				p[ 1 ].clone(),
				p[ 2 ].clone() };
		
		m.add( new PointMatch( p[ 0 ], q[ 0 ] ) );
		m.add( new PointMatch( p[ 1 ], q[ 1 ] ) );
		m.add( new PointMatch( p[ 2 ], q[ 2 ] ) );
		
		handles = new PointRoi( x, y, 3 );
		imp.setRoi( handles );
	}
	
	@Override
	final protected void updateHandles( final int x, final int y )
	{
		final float[] fq = q[ targetIndex ].getW();
			
		final int[] rx = new int[ q.length ];
		final int[] ry = new int[ q.length ];
		
		for ( int i = 0; i < q.length; ++i )
		{
			rx[ i ] = ( int )q[ i ].getW()[ 0 ];
			ry[ i ] = ( int )q[ i ].getW()[ 1 ];
		}
			
		rx[ targetIndex ] = x;
		ry[ targetIndex ] = y;
			
		handles = new PointRoi( rx, ry, 3 );
		imp.setRoi( handles );
			
		fq[ 0 ] = x;
		fq[ 1 ] = y;
	}
}
