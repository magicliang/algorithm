package algorithm.foundations.divideconquer;

/**
 * 快速傅里叶变换 (FFT) 算法实现。
 *
 * 问题描述：
 * 计算离散傅里叶变换 (DFT)，将时域信号转换为频域信号。
 * 传统 DFT 算法时间复杂度为 O(n²)，FFT 通过分治策略将复杂度降低到 O(n log n)。
 *
 * 算法原理：
 * FFT 基于 Cooley-Tukey 算法，利用单位根的周期性质：
 * 1. 将 n 点 DFT 分解为两个 n/2 点 DFT
 * 2. 分别计算偶数索引和奇数索引的 DFT
 * 3. 利用蝶形运算合并结果
 *
 * 数学公式：
 * X[k] = Σ(n=0 to N-1) x[n] * e^(-j*2π*k*n/N)
 * 其中 e^(-j*2π/N) 是 N 次单位根
 *
 * 分治思想体现：
 * 1. 分 (Divide)：将序列按奇偶索引分为两部分
 * 2. 治 (Conquer)：递归计算两个子序列的 FFT
 * 3. 合 (Combine)：通过蝶形运算合并结果
 *
 * 时间复杂度：T(n) = 2T(n/2) + O(n) = O(n log n)
 * 空间复杂度：O(n log n)，递归栈深度为 O(log n)
 *
 * 应用场景：
 * - 信号处理和频谱分析
 * - 数字图像处理
 * - 多项式乘法
 * - 卷积运算
 * - 音频和视频压缩
 *
 * @author magicliang
 * @date 2025-09-09 12:30
 */
public class FFT {

    /**
     * 快速傅里叶变换主入口。
     * 步骤：
     * 1. 参数验证和预处理
     * 2. 将输入长度扩展到2的幂次
     * 3. 调用递归的 FFT 算法
     * 4. 返回变换结果
     *
     * @param input 输入信号（复数数组）
     * @return FFT 变换结果
     */
    public static Complex[] fft(Complex[] input) {
        if (input == null || input.length == 0) {
            throw new IllegalArgumentException("输入不能为空");
        }

        int n = input.length;

        // 将长度扩展到2的幂次
        int powerOfTwo = 1;
        while (powerOfTwo < n) {
            powerOfTwo <<= 1;
        }

        // 如果需要，用0填充
        Complex[] paddedInput = new Complex[powerOfTwo];
        System.arraycopy(input, 0, paddedInput, 0, n);
        for (int i = n; i < powerOfTwo; i++) {
            paddedInput[i] = new Complex(0, 0);
        }

        return fftRecursive(paddedInput);
    }

    /**
     * 递归的 FFT 算法实现。
     * 步骤：
     * 1. 递归基：长度为1直接返回
     * 2. 分：将序列按奇偶索引分为两部分
     * 3. 治：递归计算两个子序列的 FFT
     * 4. 合：通过蝶形运算合并结果
     *
     * @param input 输入序列
     * @return FFT 结果
     */
    private static Complex[] fftRecursive(Complex[] input) {
        int n = input.length;

        // 递归基：长度为1
        if (n == 1) {
            return new Complex[]{input[0]};
        }

        // 分：按奇偶索引分离
        Complex[] even = new Complex[n / 2];
        Complex[] odd = new Complex[n / 2];

        for (int i = 0; i < n / 2; i++) {
            even[i] = input[2 * i];      // 偶数索引
            odd[i] = input[2 * i + 1];   // 奇数索引
        }

        // 治：递归计算子问题
        Complex[] evenFFT = fftRecursive(even);
        Complex[] oddFFT = fftRecursive(odd);

        // 合：蝶形运算合并结果
        Complex[] result = new Complex[n];
        for (int k = 0; k < n / 2; k++) {
            // 计算旋转因子 W_n^k = e^(-j*2π*k/n)
            double angle = -2 * Math.PI * k / n;
            Complex twiddle = new Complex(Math.cos(angle), Math.sin(angle));

            // 蝶形运算
            Complex t = twiddle.multiply(oddFFT[k]);
            result[k] = evenFFT[k].add(t);           // 前半部分
            result[k + n / 2] = evenFFT[k].subtract(t); // 后半部分
        }

        return result;
    }

    /**
     * 快速傅里叶逆变换 (IFFT)。
     *
     * @param input 频域信号
     * @return 时域信号
     */
    public static Complex[] ifft(Complex[] input) {
        if (input == null || input.length == 0) {
            throw new IllegalArgumentException("输入不能为空");
        }

        int n = input.length;

        // 共轭输入
        Complex[] conjugated = new Complex[n];
        for (int i = 0; i < n; i++) {
            conjugated[i] = new Complex(input[i].real, -input[i].imag);
        }

        // 执行 FFT
        Complex[] result = fft(conjugated);

        // 共轭结果并除以 n
        for (int i = 0; i < n; i++) {
            result[i] = new Complex(result[i].real / n, -result[i].imag / n);
        }

        return result;
    }

    /**
     * 使用 FFT 进行多项式乘法。
     * 两个多项式的乘积可以通过卷积计算，而卷积可以用 FFT 高效实现。
     *
     * @param poly1 第一个多项式的系数
     * @param poly2 第二个多项式的系数
     * @return 乘积多项式的系数
     */
    public static double[] polynomialMultiply(double[] poly1, double[] poly2) {
        if (poly1 == null || poly2 == null || poly1.length == 0 || poly2.length == 0) {
            return new double[0];
        }

        int resultSize = poly1.length + poly2.length - 1;

        // 找到大于等于 resultSize 的最小2的幂次
        int fftSize = 1;
        while (fftSize < resultSize) {
            fftSize <<= 1;
        }

        // 转换为复数并填充0
        Complex[] complex1 = new Complex[fftSize];
        Complex[] complex2 = new Complex[fftSize];

        for (int i = 0; i < fftSize; i++) {
            complex1[i] = new Complex(i < poly1.length ? poly1[i] : 0);
            complex2[i] = new Complex(i < poly2.length ? poly2[i] : 0);
        }

        // 执行 FFT
        Complex[] fft1 = fft(complex1);
        Complex[] fft2 = fft(complex2);

        // 点乘
        Complex[] product = new Complex[fftSize];
        for (int i = 0; i < fftSize; i++) {
            product[i] = fft1[i].multiply(fft2[i]);
        }

        // 执行 IFFT
        Complex[] ifftResult = ifft(product);

        // 提取实部作为结果
        double[] result = new double[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = Math.round(ifftResult[i].real); // 四舍五入消除浮点误差
        }

        return result;
    }

    /**
     * 生成测试用的正弦波信号。
     *
     * @param frequency 频率
     * @param sampleRate 采样率
     * @param duration 持续时间（秒）
     * @return 信号样本
     */
    public static Complex[] generateSineWave(double frequency, int sampleRate, double duration) {
        int numSamples = (int) (sampleRate * duration);
        Complex[] signal = new Complex[numSamples];

        for (int i = 0; i < numSamples; i++) {
            double t = (double) i / sampleRate;
            double amplitude = Math.sin(2 * Math.PI * frequency * t);
            signal[i] = new Complex(amplitude, 0);
        }

        return signal;
    }

    /**
     * 复数类，用于表示复数运算。
     */
    public static class Complex {

        public double real;  // 实部
        public double imag;  // 虚部

        public Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        public Complex(double real) {
            this(real, 0);
        }

        /**
         * 复数加法。
         */
        public Complex add(Complex other) {
            return new Complex(this.real + other.real, this.imag + other.imag);
        }

        /**
         * 复数减法。
         */
        public Complex subtract(Complex other) {
            return new Complex(this.real - other.real, this.imag - other.imag);
        }

        /**
         * 复数乘法。
         */
        public Complex multiply(Complex other) {
            double newReal = this.real * other.real - this.imag * other.imag;
            double newImag = this.real * other.imag + this.imag * other.real;
            return new Complex(newReal, newImag);
        }

        /**
         * 计算复数的模长。
         */
        public double magnitude() {
            return Math.sqrt(real * real + imag * imag);
        }

        /**
         * 计算复数的相位角。
         */
        public double phase() {
            return Math.atan2(imag, real);
        }

        @Override
        public String toString() {
            if (imag >= 0) {
                return String.format("%.3f + %.3fi", real, imag);
            } else {
                return String.format("%.3f - %.3fi", real, -imag);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Complex complex = (Complex) obj;
            return Math.abs(real - complex.real) < 1e-9 && Math.abs(imag - complex.imag) < 1e-9;
        }
    }
}