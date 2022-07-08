package keyboard.cheetahphotokeyboard.neon.gallery.img_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.Scroller;

public class Click_Photo_Setview extends ImageView {


    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MIN_MULTIPLIER = .75f;
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private float eagyclickphoto;
    private Matrix matrix, prevMatrix;

    private static enum State {NONE, DRAG, ZOOM, FLING, ANIMATE_ZOOM};
    private GestureDetector selectphoto;
    private State state;
    private float mainmum;
    private float maeximum;
    private float mainimumimg;
    private float maximumimg;
    private float[] floteimg;
    private Context appcont;
    private Fling imgfl;
    private ScaleType clickimg;
    private boolean photo_setview;
    private boolean startup_img;
    private ZoomVariables setimg_bigphoto;
    private int viewWidth, viewHeight, prevViewWidth, prevViewHeight;
    private float matchViewWidth, matchViewHeight, prevMatchViewWidth, prevMatchViewHeight;
    private ScaleGestureDetector gestureder;
    private GestureDetector.OnDoubleTapListener twoclickimg = null;
    private OnTouchListener onclicklicstimg = null;
    private OnTouchImageViewListener clickphotoset = null;

    public Click_Photo_Setview(Context appcont) {
        super(appcont);
        sharedConstructing(appcont);
    }

    public Click_Photo_Setview(Context appcont, AttributeSet atrbutes) {
        super(appcont, atrbutes);
        sharedConstructing(appcont);
    }

    public Click_Photo_Setview(Context appcont, AttributeSet atrbutes, int defStyle) {
        super(appcont, atrbutes, defStyle);
        sharedConstructing(appcont);
    }

    private void sharedConstructing(Context appcont) {
        super.setClickable(true);
        this.appcont = appcont;
        gestureder = new ScaleGestureDetector(appcont, new ScaleListener());
        selectphoto = new GestureDetector(appcont, new GestureListener());
        matrix = new Matrix();
        prevMatrix = new Matrix();
        floteimg = new float[9];
        eagyclickphoto = 1;
        if (clickimg == null) {
            clickimg = ScaleType.FIT_CENTER;
        }
        mainmum = 1;
        maeximum = 3;
        mainimumimg = SUPER_MIN_MULTIPLIER * mainmum;
        maximumimg = SUPER_MAX_MULTIPLIER * maeximum;
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);
        setState(State.NONE);
        startup_img = false;
        super.setOnTouchListener(new PrivateOnTouchListener());
    }

    @Override
    public void setOnTouchListener(OnTouchListener click) {
        onclicklicstimg = click;
    }

    public void setOnTouchImageViewListener(OnTouchImageViewListener click) {
        clickphotoset = click;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener click) {
        twoclickimg = click;
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setImageBitmap(Bitmap map) {
        super.setImageBitmap(map);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setImageDrawable(Drawable img) {
        super.setImageDrawable(img);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setImageURI(Uri link) {
        super.setImageURI(link);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setScaleType(ScaleType style) {
        if (style == ScaleType.FIT_START || style == ScaleType.FIT_END) {
            throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        }
        if (style == ScaleType.MATRIX) {
            super.setScaleType(ScaleType.MATRIX);

        } else {
            clickimg = style;
            if (startup_img) {
                setZoom(this);
            }
        }
    }

    @Override
    public ScaleType getScaleType() {
        return clickimg;
    }

    public boolean isZoomed() {
        return eagyclickphoto != 1;
    }

    public RectF getZoomedRect() {
        if (clickimg == ScaleType.FIT_XY) {
            throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
        }
        PointF topLeft = transformCoordTouchToBitmap(0, 0, true);
        PointF bottomRight = transformCoordTouchToBitmap(viewWidth, viewHeight, true);

        float w = getDrawable().getIntrinsicWidth();
        float h = getDrawable().getIntrinsicHeight();
        return new RectF(topLeft.x / w, topLeft.y / h, bottomRight.x / w, bottomRight.y / h);
    }

    private void savePreviousImageValues() {
        if (matrix != null && viewHeight != 0 && viewWidth != 0) {
            matrix.getValues(floteimg);
            prevMatrix.setValues(floteimg);
            prevMatchViewHeight = matchViewHeight;
            prevMatchViewWidth = matchViewWidth;
            prevViewHeight = viewHeight;
            prevViewWidth = viewWidth;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle save = new Bundle();
        save.putParcelable("instanceState", super.onSaveInstanceState());
        save.putFloat("saveScale", eagyclickphoto);
        save.putFloat("matchViewHeight", matchViewHeight);
        save.putFloat("matchViewWidth", matchViewWidth);
        save.putInt("viewWidth", viewWidth);
        save.putInt("viewHeight", viewHeight);
        matrix.getValues(floteimg);
        save.putFloatArray("matrix", floteimg);
        save.putBoolean("imageRendered", photo_setview);
        return save;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcel) {
        if (parcel instanceof Bundle) {
            Bundle restore = (Bundle) parcel;
            eagyclickphoto = restore.getFloat("saveScale");
            floteimg = restore.getFloatArray("matrix");
            prevMatrix.setValues(floteimg);
            prevMatchViewHeight = restore.getFloat("matchViewHeight");
            prevMatchViewWidth = restore.getFloat("matchViewWidth");
            prevViewHeight = restore.getInt("viewHeight");
            prevViewWidth = restore.getInt("viewWidth");
            photo_setview = restore.getBoolean("imageRendered");
            super.onRestoreInstanceState(restore.getParcelable("instanceState"));
            return;
        }

        super.onRestoreInstanceState(parcel);
    }

    @Override
    protected void onDraw(Canvas ndrew) {
        startup_img = true;
        photo_setview = true;
        if (setimg_bigphoto != null) {
            setZoom(setimg_bigphoto.size, setimg_bigphoto.xzoom, setimg_bigphoto.yzoom, setimg_bigphoto.sizestyle);
            setimg_bigphoto = null;
        }
        super.onDraw(ndrew);
    }

    @Override
    public void onConfigurationChanged(Configuration gretion) {
        super.onConfigurationChanged(gretion);
        savePreviousImageValues();
    }

    public float getMaxZoom() {
        return maeximum;
    }

    public void setMaxZoom(float max) {
        maeximum = max;
        maximumimg = SUPER_MAX_MULTIPLIER * maeximum;
    }

    public float getMinZoom() {
        return mainmum;
    }

    public float getCurrentZoom() {
        return eagyclickphoto;
    }

    public void setMinZoom(float min) {
        mainmum = min;
        mainimumimg = SUPER_MIN_MULTIPLIER * mainmum;
    }

    public void resetZoom() {
        eagyclickphoto = 1;
        fitImageToView();
    }

    public void setZoom(float zoom, float zoomx, float zoomy) {
        setZoom(zoom, zoomx, zoomy, clickimg);
    }

    public void setZoom(float size) {
        setZoom(size, 0.5f, 0.5f);
    }

    public void setZoom(float size, float xzoom, float yzoom, ScaleType scaleType) {
        if (!startup_img) {
            setimg_bigphoto = new ZoomVariables(size, xzoom, yzoom, scaleType);
            return;
        }

        if (scaleType != clickimg) {
            setScaleType(scaleType);
        }
        resetZoom();
        scaleImage(size, viewWidth / 2, viewHeight / 2, true);
        matrix.getValues(floteimg);
        floteimg[Matrix.MTRANS_X] = -((xzoom * getImageWidth()) - (viewWidth * 0.5f));
        floteimg[Matrix.MTRANS_Y] = -((yzoom * getImageHeight()) - (viewHeight * 0.5f));
        matrix.setValues(floteimg);
        fixTrans();
        setImageMatrix(matrix);
    }

    public void setZoom(Click_Photo_Setview img) {
        PointF grvati = img.getScrollPosition();
        setZoom(img.getCurrentZoom(), grvati.x, grvati.y, img.getScaleType());
    }

    public PointF getScrollPosition() {
        Drawable img = getDrawable();
        if (img == null) {
            return null;
        }
        int drawableWidth = img.getIntrinsicWidth();
        int drawableHeight = img.getIntrinsicHeight();

        PointF topik = transformCoordTouchToBitmap(viewWidth / 2, viewHeight / 2, true);
        topik.x /= drawableWidth;
        topik.y /= drawableHeight;
        return topik;
    }

    public void setScrollPosition(float focusX, float focusY) {
        setZoom(eagyclickphoto, focusX, focusY);
    }

    private void fixTrans() {
        matrix.getValues(floteimg);
        float xchang = floteimg[Matrix.MTRANS_X];
        float ychang = floteimg[Matrix.MTRANS_Y];

        float xchangimg = getFixTrans(xchang, viewWidth, getImageWidth());
        float ychangimg = getFixTrans(ychang, viewHeight, getImageHeight());

        if (xchangimg != 0 || ychangimg != 0) {
            matrix.postTranslate(xchangimg, ychangimg);
        }
    }

    private void fixScaleTrans() {
        fixTrans();
        matrix.getValues(floteimg);
        if (getImageWidth() < viewWidth) {
            floteimg[Matrix.MTRANS_X] = (viewWidth - getImageWidth()) / 2;
        }

        if (getImageHeight() < viewHeight) {
            floteimg[Matrix.MTRANS_Y] = (viewHeight - getImageHeight()) / 2;
        }
        matrix.setValues(floteimg);
    }

    private float getFixTrans(float chang, float tarnce, float contentSize) {
        float minTrans, maxTrans;

        if (contentSize <= tarnce) {
            minTrans = 0;
            maxTrans = tarnce - contentSize;

        } else {
            minTrans = tarnce - contentSize;
            maxTrans = 0;
        }

        if (chang < minTrans)
            return -chang + minTrans;
        if (chang > maxTrans)
            return -chang + maxTrans;
        return 0;
    }

    private float getFixDragTrans(float remove, float trance, float fixed) {
        if (fixed <= trance) {
            return 0;
        }
        return remove;
    }

    private float getImageWidth() {
        return matchViewWidth * eagyclickphoto;
    }

    private float getImageHeight() {
        return matchViewHeight * eagyclickphoto;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable img = getDrawable();
        if (img == null || img.getIntrinsicWidth() == 0 || img.getIntrinsicHeight() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }

        int imgwth = img.getIntrinsicWidth();
        int imhht = img.getIntrinsicHeight();
        int imgscale = MeasureSpec.getSize(widthMeasureSpec);
        int imgmd = MeasureSpec.getMode(widthMeasureSpec);
        int imgscaleht = MeasureSpec.getSize(heightMeasureSpec);
        int hitmd = MeasureSpec.getMode(heightMeasureSpec);
        viewWidth = setViewSize(imgmd, imgscale, imgwth);
        viewHeight = setViewSize(hitmd, imgscaleht, imhht);
        setMeasuredDimension(viewWidth, viewHeight);
        fitImageToView();
    }

    private void fitImageToView() {
        Drawable img = getDrawable();
        if (img == null || img.getIntrinsicWidth() == 0 || img.getIntrinsicHeight() == 0) {
            return;
        }
        if (matrix == null || prevMatrix == null) {
            return;
        }

        int drawableWidth = img.getIntrinsicWidth();
        int drawableHeight = img.getIntrinsicHeight();
        float xsize = (float) viewWidth / drawableWidth;
        float ysize = (float) viewHeight / drawableHeight;

        switch (clickimg) {
            case CENTER:
                xsize = ysize = 1;
                break;

            case CENTER_CROP:
                xsize = ysize = Math.max(xsize, ysize);
                break;

            case CENTER_INSIDE:
                xsize = ysize = Math.min(1, Math.min(xsize, ysize));

            case FIT_CENTER:
                xsize = ysize = Math.min(xsize, ysize);
                break;

            case FIT_XY:
                break;

            default:
                throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");

        }
        float jagyawth = viewWidth - (xsize * drawableWidth);
        float viewjagya = viewHeight - (ysize * drawableHeight);
        matchViewWidth = viewWidth - jagyawth;
        matchViewHeight = viewHeight - viewjagya;
        if (!isZoomed() && !photo_setview) {
            matrix.setScale(xsize, ysize);
            matrix.postTranslate(jagyawth / 2, viewjagya / 2);
            eagyclickphoto = 1;

        } else {
            if (prevMatchViewWidth == 0 || prevMatchViewHeight == 0) {
                savePreviousImageValues();
            }

            prevMatrix.getValues(floteimg);
            floteimg[Matrix.MSCALE_X] = matchViewWidth / drawableWidth * eagyclickphoto;
            floteimg[Matrix.MSCALE_Y] = matchViewHeight / drawableHeight * eagyclickphoto;
            float xchang = floteimg[Matrix.MTRANS_X];
            float ychang = floteimg[Matrix.MTRANS_Y];
            float perfectw = prevMatchViewWidth * eagyclickphoto;
            float exitew = getImageWidth();
            translateMatrixAfterRotate(Matrix.MTRANS_X, xchang, perfectw, exitew, prevViewWidth, viewWidth, drawableWidth);
            float perfecthi = prevMatchViewHeight * eagyclickphoto;
            float exitewht = getImageHeight();
            translateMatrixAfterRotate(Matrix.MTRANS_Y, ychang, perfecthi, exitewht, prevViewHeight, viewHeight, drawableHeight);
            matrix.setValues(floteimg);
        }
        fixTrans();
        setImageMatrix(matrix);
    }

    private int setViewSize(int mode, int size, int drawableWidth) {
        int sccalesize;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                sccalesize = size;
                break;

            case MeasureSpec.AT_MOST:
                sccalesize = Math.min(drawableWidth, size);
                break;

            case MeasureSpec.UNSPECIFIED:
                sccalesize = drawableWidth;
                break;

            default:
                sccalesize = size;
                break;
        }
        return sccalesize;
    }

    private void translateMatrixAfterRotate(int axis, float trans, float prevImageSize, float imageSize, int prevViewSize, int viewSize, int drawableSize) {
        if (imageSize < viewSize) {
            floteimg[axis] = (viewSize - (drawableSize * floteimg[Matrix.MSCALE_X])) * 0.5f;

        } else if (trans > 0) {
            floteimg[axis] = -((imageSize - viewSize) * 0.5f);

        } else {
            float taka = (Math.abs(trans) + (0.5f * prevViewSize)) / prevImageSize;
            floteimg[axis] = -((taka * imageSize) - (viewSize * 0.5f));
        }
    }

    private void setState(State imgset) {
        this.state = imgset;
    }

    public boolean canScrollHorizontallyFroyo(int direction) {
        return canScrollHorizontally(direction);
    }

    @Override
    public boolean canScrollHorizontally(int degree) {
        matrix.getValues(floteimg);
        float x = floteimg[Matrix.MTRANS_X];

        if (getImageWidth() < viewWidth) {
            return false;

        } else if (x >= -1 && degree < 0) {
            return false;

        } else if (Math.abs(x) + viewWidth + 1 >= getImageWidth() && degree > 0) {
            return false;
        }

        return true;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            if (twoclickimg != null) {
                return twoclickimg.onSingleTapConfirmed(event);
            }
            return performClick();
        }

        @Override
        public void onLongPress(MotionEvent event) {
            performLongClick();
        }

        @Override
        public boolean onFling(MotionEvent mevent, MotionEvent nevent, float xfling, float yfling) {
            if (imgfl != null) {
                imgfl.cancelFling();
            }
            imgfl = new Fling((int) xfling, (int) yfling);
            compatPostOnAnimation(imgfl);
            return super.onFling(mevent, nevent, xfling, yfling);
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            boolean consumed = false;
            if (twoclickimg != null) {
                consumed = twoclickimg.onDoubleTap(event);
            }
            if (state == State.NONE) {
                float goal = (eagyclickphoto == mainmum) ? maeximum : mainmum;
                DoubleTapZoom twoclick = new DoubleTapZoom(goal, event.getX(), event.getY(), false);
                compatPostOnAnimation(twoclick);
                consumed = true;
            }
            return consumed;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent event) {
            if (twoclickimg != null) {
                return twoclickimg.onDoubleTapEvent(event);
            }
            return false;
        }
    }

    public interface OnTouchImageViewListener {
        public void onMove();
    }

    private class PrivateOnTouchListener implements OnTouchListener {

        private PointF last = new PointF();

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureder.onTouchEvent(event);
            selectphoto.onTouchEvent(event);
            PointF click = new PointF(event.getX(), event.getY());

            if (state == State.NONE || state == State.DRAG || state == State.FLING) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        last.set(click);
                        if (imgfl != null)
                            imgfl.cancelFling();
                        setState(State.DRAG);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (state == State.DRAG) {
                            float xremove = click.x - last.x;
                            float yremove = click.y - last.y;
                            float xchenge = getFixDragTrans(xremove, viewWidth, getImageWidth());
                            float ychenge = getFixDragTrans(yremove, viewHeight, getImageHeight());
                            matrix.postTranslate(xchenge, ychenge);
                            fixTrans();
                            last.set(click.x, click.y);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        setState(State.NONE);
                        break;
                }
            }

            setImageMatrix(matrix);

            if (onclicklicstimg != null) {
                onclicklicstimg.onTouch(v, event);
            }

            if (clickphotoset != null) {
                clickphotoset.onMove();
            }

            return true;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector scale) {
            setState(State.ZOOM);
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector scalesize) {
            scaleImage(scalesize.getScaleFactor(), scalesize.getFocusX(), scalesize.getFocusY(), true);
            if (clickphotoset != null) {
                clickphotoset.onMove();
            }
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector geature) {
            super.onScaleEnd(geature);
            setState(State.NONE);
            boolean animateToZoomBoundary = false;
            float targetZoom = eagyclickphoto;
            if (eagyclickphoto > maeximum) {
                targetZoom = maeximum;
                animateToZoomBoundary = true;

            } else if (eagyclickphoto < mainmum) {
                targetZoom = mainmum;
                animateToZoomBoundary = true;
            }

            if (animateToZoomBoundary) {
                DoubleTapZoom twoclick = new DoubleTapZoom(targetZoom, viewWidth / 2, viewHeight / 2, true);
                compatPostOnAnimation(twoclick);
            }
        }
    }

    private void scaleImage(double removesize, float ximg, float yimg, boolean stretchImageToSuper) {

        float endsize, topsize;
        if (stretchImageToSuper) {
            endsize = mainimumimg;
            topsize = maximumimg;

        } else {
            endsize = mainmum;
            topsize = maeximum;
        }

        float img = eagyclickphoto;
        eagyclickphoto *= removesize;
        if (eagyclickphoto > topsize) {
            eagyclickphoto = topsize;
            removesize = topsize / img;
        } else if (eagyclickphoto < endsize) {
            eagyclickphoto = endsize;
            removesize = endsize / img;
        }

        matrix.postScale((float) removesize, (float) removesize, ximg, yimg);
        fixScaleTrans();
    }

    private class DoubleTapZoom implements Runnable {

        private long onwatch;
        private static final float BIGIMG = 500;
        private float onbig, goalzom;
        private float ximgview, yimgview;
        private boolean doubalezoom;
        private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        private PointF onclick;
        private PointF botomclick;

        DoubleTapZoom(float targetZoom, float focusX, float focusY, boolean stretchImageToSuper) {
            setState(State.ANIMATE_ZOOM);
            onwatch = System.currentTimeMillis();
            this.onbig = eagyclickphoto;
            this.goalzom = targetZoom;
            this.doubalezoom = stretchImageToSuper;
            PointF bitmapPoint = transformCoordTouchToBitmap(focusX, focusY, false);
            this.ximgview = bitmapPoint.x;
            this.yimgview = bitmapPoint.y;

            onclick = transformCoordBitmapToTouch(ximgview, yimgview);
            botomclick = new PointF(viewWidth / 2, viewHeight / 2);
        }

        @Override
        public void run() {
            float mrun = interpolate();
            double removesize = calculateDeltaScale(mrun);
            scaleImage(removesize, ximgview, yimgview, doubalezoom);
            translateImageToCenterTouchPosition(mrun);
            fixScaleTrans();
            setImageMatrix(matrix);
            if (clickphotoset != null) {
                clickphotoset.onMove();
            }

            if (mrun < 1f) {
                compatPostOnAnimation(this);

            } else {
                setState(State.NONE);
            }
        }

        private void translateImageToCenterTouchPosition(float target) {
            float xgoal = onclick.x + target * (botomclick.x - onclick.x);
            float ygoal = onclick.y + target * (botomclick.y - onclick.y);
            PointF point = transformCoordBitmapToTouch(ximgview, yimgview);
            matrix.postTranslate(xgoal - point.x, ygoal - point.y);
        }

        private float interpolate() {
            long whatch = System.currentTimeMillis();
            float inter = (whatch - onwatch) / BIGIMG;
            inter = Math.min(1f, inter);
            return interpolator.getInterpolation(inter);
        }

        private double calculateDeltaScale(float t) {
            double zoom = onbig + t * (goalzom - onbig);
            return zoom / eagyclickphoto;
        }
    }

    private PointF transformCoordTouchToBitmap(float x, float y, boolean clipToBitmap) {
        matrix.getValues(floteimg);
        float curentwith = getDrawable().getIntrinsicWidth();
        float curenthight = getDrawable().getIntrinsicHeight();
        float xcurentimg = floteimg[Matrix.MTRANS_X];
        float ycurentimg = floteimg[Matrix.MTRANS_Y];
        float xend = ((x - xcurentimg) * curentwith) / getImageWidth();
        float yend = ((y - ycurentimg) * curenthight) / getImageHeight();

        if (clipToBitmap) {
            xend = Math.min(Math.max(xend, 0), curentwith);
            yend = Math.min(Math.max(yend, 0), curenthight);
        }

        return new PointF(xend, yend);
    }

    private PointF transformCoordBitmapToTouch(float bx, float by) {
        matrix.getValues(floteimg);
        float curentweth = getDrawable().getIntrinsicWidth();
        float curenthight = getDrawable().getIntrinsicHeight();
        float ximg = bx / curentweth;
        float yimg = by / curenthight;
        float xend = floteimg[Matrix.MTRANS_X] + getImageWidth() * ximg;
        float yend = floteimg[Matrix.MTRANS_Y] + getImageHeight() * yimg;
        return new PointF(xend, yend);
    }

    private class Fling implements Runnable {

        CompatScroller swabe;
        int xorignal, yorignal;

        Fling(int velocityX, int velocityY) {
            setState(State.FLING);
            swabe = new CompatScroller(appcont);
            matrix.getValues(floteimg);

            int startX = (int) floteimg[Matrix.MTRANS_X];
            int startY = (int) floteimg[Matrix.MTRANS_Y];
            int minX, maxX, minY, maxY;

            if (getImageWidth() > viewWidth) {
                minX = viewWidth - (int) getImageWidth();
                maxX = 0;

            } else {
                minX = maxX = startX;
            }

            if (getImageHeight() > viewHeight) {
                minY = viewHeight - (int) getImageHeight();
                maxY = 0;

            } else {
                minY = maxY = startY;
            }

            swabe.fling(startX, startY, (int) velocityX, (int) velocityY, minX, maxX, minY, maxY);
            xorignal = startX;
            yorignal = startY;
        }

        public void cancelFling() {
            if (swabe != null) {
                setState(State.NONE);
                swabe.forceFinished(true);
            }
        }

        @Override
        public void run() {

            if (clickphotoset != null) {
                clickphotoset.onMove();
            }

            if (swabe.isFinished()) {
                swabe = null;
                return;
            }

            if (swabe.computeScrollOffset()) {
                int ximg = swabe.getCurrX();
                int yimg = swabe.getCurrY();
                int xchange = ximg - xorignal;
                int ychange = yimg - this.yorignal;
                xorignal = ximg;
                this.yorignal = yimg;
                matrix.postTranslate(xchange, ychange);
                fixTrans();
                setImageMatrix(matrix);
                compatPostOnAnimation(this);
            }
        }
    }

    @TargetApi(VERSION_CODES.GINGERBREAD)
    private class CompatScroller {
        Scroller swabe;
        OverScroller endswabe;
        boolean compescorl;

        public CompatScroller(Context context) {
            if (VERSION.SDK_INT < VERSION_CODES.GINGERBREAD) {
                compescorl = true;
                swabe = new Scroller(context);

            } else {
                compescorl = false;
                endswabe = new OverScroller(context);
            }
        }

        public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
            if (compescorl) {
                swabe.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            } else {
                endswabe.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            }
        }

        public void forceFinished(boolean finished) {
            if (compescorl) {
                swabe.forceFinished(finished);
            } else {
                endswabe.forceFinished(finished);
            }
        }

        public boolean isFinished() {
            if (compescorl) {
                return swabe.isFinished();
            } else {
                return endswabe.isFinished();
            }
        }

        public boolean computeScrollOffset() {
            if (compescorl) {
                return swabe.computeScrollOffset();
            } else {
                endswabe.computeScrollOffset();
                return endswabe.computeScrollOffset();
            }
        }

        public int getCurrX() {
            if (compescorl) {
                return swabe.getCurrX();
            } else {
                return endswabe.getCurrX();
            }
        }

        public int getCurrY() {
            if (compescorl) {
                return swabe.getCurrY();
            } else {
                return endswabe.getCurrY();
            }
        }
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN)
    private void compatPostOnAnimation(Runnable runnable) {
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            postOnAnimation(runnable);
        } else {
            postDelayed(runnable, 1000 / 60);
        }
    }

    private class ZoomVariables {
        public float size;
        public float xzoom;
        public float yzoom;
        public ScaleType sizestyle;

        public ZoomVariables(float scale, float focusX, float focusY, ScaleType scaleType) {
            this.size = scale;
            this.xzoom = focusX;
            this.yzoom = focusY;
            this.sizestyle = scaleType;
        }
    }

    private void printMatrixInfo() {
        float[] n = new float[9];
        matrix.getValues(n);
        Log.d(DEBUG, "Scale: " + n[Matrix.MSCALE_X] + " TransX: " + n[Matrix.MTRANS_X] + " TransY: " + n[Matrix.MTRANS_Y]);
    }
}