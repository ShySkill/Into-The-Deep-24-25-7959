package org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.Pipelines;

import android.annotation.SuppressLint;

import org.openftc.easyopencv.OpenCvPipeline;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class ItWasAllRedAndYellow extends OpenCvPipeline
{

    Mat ycrcbMat = new Mat();
    Mat crMat = new Mat();
    Mat cbMat = new Mat();

    Mat blueThresholdMat = new Mat();
    Mat redThresholdMat = new Mat();
    Mat yellowThresholdMat = new Mat();

    Mat morphedBlueThreshold = new Mat();
    Mat morphedRedThreshold = new Mat();
    Mat morphedYellowThreshold = new Mat();

    Mat contoursOnPlainImageMat = new Mat();


    static final int YELLOW_MASK_THRESHOLD = 57;
    static final int BLUE_MASK_THRESHOLD = 150;
    static final int RED_MASK_THRESHOLD = 173;


    Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3.5, 3.5));
    Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3.5, 3.5));


    //all of the colors, blue is redundant but we use for thresholding
    static final Scalar PINK = new Scalar(255,20,147);
    static final Scalar RED = new Scalar(255, 0, 0);
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar YELLOW = new Scalar(255, 255, 0);
    static final Scalar GREEN = new Scalar(0, 255, 0);
    static final Scalar PURPLE = new Scalar(128,0,128);

    double rectArea;
    double min_rect_area = 5000;

    static int font = Imgproc.FONT_HERSHEY_SIMPLEX;
    static double fontScale = 0.5;
    static int thickness = 1;
    static int radius = 5;
    static final int CONTOUR_LINE_THICKNESS = 2;

    //changed the analyzed stone class (originally rect class) so that we could incude angles and what not
    static class AnalyzedStone{

        private final double angle;
        private final double area;
        private final String color;
        private final Point center;
        private final RotatedRect rect;

        public AnalyzedStone(double angle, String color, RotatedRect rect){
            this.angle = angle;
            this.color = color;
            this.center = rect.center;
            this.rect = rect;
            this.area = rect.size.height * rect.size.width;
        }

    }

    ArrayList<AnalyzedStone> internalStoneList = new ArrayList<>();
    static volatile ArrayList<AnalyzedStone> clientStoneList = new ArrayList<>();
    static AnalyzedStone pluseboStone = new AnalyzedStone(0, "No Sample Detected", new RotatedRect());


    enum Stage
    {
        FINAL,
        YCrCb,
        MASKS,
        MASKS_NR,
        CONTOURS;
    }

    Stage[] stages = Stage.values();
    int stageNum = 0;


    @Override
    public void onViewportTapped()
    {
        int nextStageNum = stageNum + 1;

        if(nextStageNum >= stages.length)
        {
            nextStageNum = 0;
        }

        stageNum = nextStageNum;
    }

    @Override
    public Mat processFrame(Mat input)
    {
        internalStoneList.clear();
        findContours(input);

        clientStoneList = new ArrayList<>(internalStoneList);

        switch (stages[stageNum])
        {
            case YCrCb:
            {
                return ycrcbMat;
            }

            case MASKS:
            {
                Mat masks = new Mat();
                Core.addWeighted(yellowThresholdMat, 1.0, redThresholdMat, 1.0, 0.0, masks);
                Core.addWeighted(masks, 1.0, blueThresholdMat, 1.0, 0.0, masks);
                return masks;
            }

            case MASKS_NR:
            {
                Mat masksNR = new Mat();
                Core.addWeighted(morphedYellowThreshold, 1.0, morphedRedThreshold, 1.0, 0.0, masksNR);
                Core.addWeighted(masksNR, 1.0, morphedBlueThreshold, 1.0, 0.0, masksNR);
                return masksNR;
            }

            case CONTOURS:
            {
                return contoursOnPlainImageMat;
            }

            default:
            {
                return input;
            }
        }
    }

    public static ArrayList<AnalyzedStone> getRectList()
    {
        return clientStoneList;
    }

    public static double getCoordsX(){
        AnalyzedStone rect = findFarthestRectangle(getRectList());
        return rect.center.x;
    }
    public static double getCoordsY(){
        AnalyzedStone rect = findFarthestRectangle(getRectList());
        return rect.center.y;
    }

    public static double getAngle(){
        return findFarthestRectangle(getRectList()).angle;
    }

    public static String getColor(){
        return findFarthestRectangle(getRectList()).color;
    }

    void findContours(Mat input)
    {
        Imgproc.cvtColor(input, ycrcbMat, Imgproc.COLOR_RGB2YCrCb);

        Core.extractChannel(ycrcbMat, cbMat, 2); // Cb channel index is 2
        Core.extractChannel(ycrcbMat, crMat, 1); // Cr channel index is 1

        Imgproc.threshold(cbMat, blueThresholdMat, BLUE_MASK_THRESHOLD, 255, Imgproc.THRESH_BINARY);
        Imgproc.threshold(crMat, redThresholdMat, RED_MASK_THRESHOLD, 255, Imgproc.THRESH_BINARY);
        Imgproc.threshold(cbMat, yellowThresholdMat, YELLOW_MASK_THRESHOLD, 255, Imgproc.THRESH_BINARY_INV);

        morphMask(blueThresholdMat, morphedBlueThreshold);
        morphMask(redThresholdMat, morphedRedThreshold);
        morphMask(yellowThresholdMat, morphedYellowThreshold);

        ArrayList<MatOfPoint> blueContoursList = new ArrayList<>();
        Imgproc.findContours(morphedBlueThreshold, blueContoursList, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        ArrayList<MatOfPoint> redContoursList = new ArrayList<>();
        Imgproc.findContours(morphedRedThreshold, redContoursList, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        ArrayList<MatOfPoint> yellowContoursList = new ArrayList<>();
        Imgproc.findContours(morphedYellowThreshold, yellowContoursList, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        contoursOnPlainImageMat = Mat.zeros(input.size(), input.type());


        //only drawing the red rectangles to the mat to make sure that we do not oerload the caera with too much work.
        //hello jacob M
        for(MatOfPoint contour : redContoursList)
        {
            analyzeContour(contour, input, "Red");
            contour.release();
        }

        drawBestRect(findFarthestRectangle(getRectList()), input);

    }

    void morphMask(Mat input, Mat output)
    {

        Imgproc.erode(input, output, erodeElement);
        Imgproc.erode(output, output, erodeElement);

        Imgproc.dilate(output, output, dilateElement);
        Imgproc.dilate(output, output, dilateElement);
    }

    void analyzeContour(MatOfPoint contour, Mat input, String color)
    {
        Point[] points = contour.toArray();
        MatOfPoint2f contour2f = new MatOfPoint2f(points);

        RotatedRect rotatedRectFitToContour = Imgproc.minAreaRect(contour2f);
        rectArea = rotatedRectFitToContour.size.width * rotatedRectFitToContour.size.height;

        if(rectArea < min_rect_area){
            return;
        }//check if it looks like a square
        else if(Math.abs(rotatedRectFitToContour.size.width - rotatedRectFitToContour.size.height) < 50 ){
            return;
        }

        drawRotatedRect(rotatedRectFitToContour, input, color);
        drawRotatedRect(rotatedRectFitToContour, contoursOnPlainImageMat, color);

        double rotRectAngle = rotatedRectFitToContour.angle;
        if (rotatedRectFitToContour.size.width < rotatedRectFitToContour.size.height)
        {
            rotRectAngle += 90;
        }

        double angle = -(rotRectAngle - 180);
        drawTagText(rotatedRectFitToContour, Integer.toString((int) Math.round(angle)) + " deg", input, color);

        AnalyzedStone analyzedStone = new AnalyzedStone(rotRectAngle, color, rotatedRectFitToContour);
        internalStoneList.add(analyzedStone);
    }

    static void drawTagText(RotatedRect rect, String text, Mat mat, String color)
    {
        Scalar colorScalar = getColorScalar(color);

        Imgproc.putText(
                mat,
                text,
                new Point(
                        rect.center.x - 50,
                        rect.center.y + 25),
                Imgproc.FONT_HERSHEY_PLAIN,
                1,
                GREEN,
                1);
    }

    static void drawRotatedRect(RotatedRect rect, Mat drawOn, String color)
    {

        Point[] points = new Point[4];
        rect.points(points);
        Scalar colorScalar = getColorScalar(color);

        for (int i = 0; i < 4; ++i)
        {
            Imgproc.line(drawOn, points[i], points[(i + 1) % 4], GREEN, 2);
        }

        @SuppressLint("DefaultLocale") String coordsText = String.format("(%.1f, %.1f)", rect.center.x, rect.center.y);
        String areaText = "" + Math.round(rect.size.height * rect.size.width);
        //Imgproc.putText(drawOn, coordsText, rect.center, font, fontScale, GREEN, thickness);
        Imgproc.circle(drawOn, rect.center, radius, GREEN, Imgproc.FILLED);
        Imgproc.putText(drawOn, areaText, rect.center, font, fontScale, GREEN, thickness);
    }


    static void drawBestRect(AnalyzedStone stone, Mat drawOn)
    {
        Point[] points = new Point[4];
        stone.rect.points(points);

        for (int i = 0; i < 4; ++i)
        {
            Imgproc.line(drawOn, points[i], points[(i + 1) % 4], PINK, 2);
        }
        Point center = stone.center;
        @SuppressLint("DefaultLocale") String coordsText = String.format("(%.1f, %.1f)", center.x, center.y);

        Imgproc.putText(drawOn, coordsText, center, font, fontScale, PURPLE, thickness);
        Imgproc.circle(drawOn, center, radius, PURPLE, Imgproc.FILLED);
    }


    static AnalyzedStone findFarthestRectangle(ArrayList<AnalyzedStone> clientStoneList) {

        if (clientStoneList.isEmpty()) {
            clientStoneList.add(pluseboStone);
        }

        ArrayList<AnalyzedStone> stonesCopy = new ArrayList<>(clientStoneList);
        AnalyzedStone farthestRectangle = null;
        double maxTotalDistance = -1;

        //iterate through each rectangle
        for (AnalyzedStone rect : stonesCopy) {
            double totalDistance = 0;
            Point center1 = rect.center;

            //calculate the sum of distances from this rectangle to all others
            for (AnalyzedStone otherRect : stonesCopy) {
                if (rect != otherRect) { //avoid comparing the rectangle with itself
                    Point center2 = otherRect.center;
                    totalDistance += euclideanDistance(center1, center2);
                }
            }

            //update the farthest rectangle if a larger total distance is found
            if (totalDistance > maxTotalDistance) {
                maxTotalDistance = totalDistance;
                farthestRectangle = rect;
            }
        }

        return farthestRectangle;
    }

    static double euclideanDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }



    static Scalar getColorScalar(String color)
    {
        if (color.equals("Red")) {
            return RED;
        }
        return YELLOW;
    }
}