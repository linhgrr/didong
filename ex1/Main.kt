import kotlin.math.abs

class PhanSo(private var tuSo: Int = 0, private var mauSo: Int = 1) {

    fun nhapPhanSo() {
        var tu: Int
        var mau: Int
        
        do {
            print("Input tu so (khac 0): ")
            tu = readln().toIntOrNull() ?: 0
            if (tu == 0) {
                println("vui long' nhap tu so khac 0")
            }
        } while (tu == 0)
        
        do {
            print("Input mau so (khac 0): ")
            mau = readln().toIntOrNull() ?: 0
            if (mau == 0) {
                println("vui long' nhap mau so khac 0")
            }
        } while (mau == 0)
        
        tuSo = tu
        mauSo = mau
        
        if (mauSo < 0) {
            tuSo = -tuSo
            mauSo = -mauSo
        }
    }
    
    fun inPhanSo() {
        if (mauSo == 1) {
            print("$tuSo")
        } else {
            print("$tuSo/$mauSo")
        }
    }
    
    private fun ucln(a: Int, b: Int): Int {
        val absA = abs(a)
        val absB = abs(b)
        return if (absB == 0) absA else ucln(absB, absA % absB)
    }
    
    fun toiGian() {
        val uc = ucln(tuSo, mauSo)
        tuSo /= uc
        mauSo /= uc
        
        if (mauSo < 0) {
            tuSo = -tuSo
            mauSo = -mauSo
        }
    }

    fun soSanh(phanSoKhac: PhanSo): Int {
        val tich1 = this.tuSo * phanSoKhac.mauSo
        val tich2 = phanSoKhac.tuSo * this.mauSo
        
        return when {
            tich1 < tich2 -> -1
            tich1 > tich2 -> 1
            else -> 0
        }
    }
    
    fun cong(phanSoKhac: PhanSo): PhanSo {
        val tuMoi = this.tuSo * phanSoKhac.mauSo + phanSoKhac.tuSo * this.mauSo
        val mauMoi = this.mauSo * phanSoKhac.mauSo
        val ketQua = PhanSo(tuMoi, mauMoi)
        ketQua.toiGian()
        return ketQua
    }
    
    fun toDouble(): Double {
        return tuSo.toDouble() / mauSo.toDouble()
    }
    
    override fun toString(): String {
        return if (mauSo == 1) "$tuSo" else "$tuSo/$mauSo"
    }
    
    fun copy(): PhanSo {
        return PhanSo(tuSo, mauSo)
    }
}

fun main() {
    print("nhap so luong phan tu: ")
    val n = readln().toIntOrNull() ?: 0
    
    if (n <= 0) {
        println("so luong phai > 0 T.T")
        return
    }
    
    val mangPhanSo = Array(n) { PhanSo() }
    
    println("\n1. Input mang phan so:")
    for (i in 0 until n) {
        println("Input phan so thu ${i + 1}:")
        mangPhanSo[i].nhapPhanSo()
    }
    
    println("\n2. Mang phan so: ")
    for (i in 0 until n) {
        mangPhanSo[i].inPhanSo()
        print("\t")
    }
    
    println("\n3. Mang phan so sau khi toi gian:")
    for (i in 0 until n) {
        mangPhanSo[i].toiGian()
        mangPhanSo[i].inPhanSo()
        print("\t")
    }
    
    println("\n4. Sum:")
    var tong = PhanSo(0, 1)
    for (phanSo in mangPhanSo) {
        tong = tong.cong(phanSo)
    }
    print("Sum = ")
    tong.inPhanSo()
    println()
    
    println("\n5. Phan so max:")
    var phanSoMax = mangPhanSo[0]
    var viTriMax = 0
    
    for (i in 1 until n) {
        if (mangPhanSo[i].soSanh(phanSoMax) > 0) {
            phanSoMax = mangPhanSo[i]
            viTriMax = i
        }
    }
    
    print("Phan so max: ")
    phanSoMax.inPhanSo()
    println(" (tai vi tri' ${viTriMax + 1})")
    
    println("\n6. Mang phan so sau khi sap xep:")
    
    for (i in 0 until n - 1) {
        for (j in 0 until n - 1 - i) {
            if (mangPhanSo[j].soSanh(mangPhanSo[j + 1]) < 0) {
                val temp = mangPhanSo[j].copy()
                mangPhanSo[j] = mangPhanSo[j + 1].copy()
                mangPhanSo[j + 1] = temp
            }
        }
    }
    
    for (i in 0 until n) {
        mangPhanSo[i].inPhanSo()
        print("\t")
    }
}
