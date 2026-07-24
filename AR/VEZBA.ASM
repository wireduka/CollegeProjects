! inicijalizacija simulacije
dc x.1, x.9996
dc x.0, x.9998
onkp false, x.1, x.1
onkp false, x.1, x.1
onkp true, x.1, x.1
ondma x.1, x.1
ondma x.1, x.1
ondma x.1, x.1
ondma x.1, x.1
kpreg 1.1, r0, x.1
kpreg 1.1, r1, x.2
kpreg 1.1, r2, x.3
kpreg 1.1, r3, x.4
kpreg 1.1, r4, x.5
kpreg 1.1, r5, x.6
kpreg 1.1, r6, x.7
kpreg 1.1, r7, x.8
kpreg 2.1, r0, x.9
kpreg 2.1, r1, x.a
kpreg 2.1, r2, x.b
kpreg 2.1, r3, x.c
kpreg 2.1, r4, x.d
kpreg 2.1, r5, x.e
kpreg 2.1, r6, x.f
kpreg 2.1, r7, x.10
reg pc, x.4000
reg ivtp, x.4000
reg sp, x.9000
! kraj inicijalizacije
! glavni program
org x.4000

! inicijalizacija ivtp tabele
ldimm x.100,r0
mvrir r0,ivtp 			! IVTP <= 100h

ldimm x.3000,r0
stmem x.100,r0 			! ulaz 0 <= 3000h

ldimm x.2500,r0
stmem x.101,r0 			! ulaz 1 <= 2500h

ldimm x.2000,r0
stmem x.102,r0 			! ulaz 2 <= 2000h

ldimm x.1500,r0
stmem x.103,r0 			! ulaz 3 <= 1500h

ldimm x.1000,r0
stmem x.104,r0			! ulaz 4 <= 1000h

ldimm x.500,r0
stmem x.105,r0			! ulaz 5 <= 500h

! startovanje periferije KP2.1 (ispitivanje ready)
ldimm x.8,r1 			! r1 <= ArrLengthB(KP2.1)
ldimm x.6000,r2			! r2 <= ArrDestB(KP2.1)
ldimm x.5,r0
stmem x.F200,r0			! KP2.1 <= start <= 5h

! startovanje periferije KP1.1 (prekid)
ldimm x.8,r3			! r3 <= ArrLengthA(KP1.1)
ldimm x.5000,r4			! r4 <= ArrDestA(KP1.1)
ldimm x.0,r5			! r5 <= semafor

ldimm x.5,r0
stmem x.F102,r0			! KP1.1 <= entry <= 5h

ldimm x.F,r0
stmem x.F100,r0			! KP1.1 <= START <= Fh

! zauzeti registri: r1,r2,r3,r4,r5
! ispitivanje bita spremnosti KP2.1
ldimm x.1,r6
wait21:ldmem x.F201,r7
and r7,r7,r6

! zauzeti registri: r1,r2,r3,r4,r5,r6,r7
! ready bit KP2.1 postavljen na 1
ldmem x.F203,r0
stri [r2],r0
inc r2
dec r1
bneq wait21

! KP2.1 zavrsio sa radom
! KP2.1 iskljucivanje
ldimm x.0,r0
stmem x.F200,r0

! provera je l KP1.1 zavrsio sa radom
ldimm x.0,r9
wait11: ldmem x.F100,r8
cmp r8,r9
bneq wait11
! gotove obe periferije sa radom, zauzeti registri: /

! pozivanje f-je void xorArr(int* arr1, int* arr2, int n)
ldimm x.8,r0
push r0		! int n (8)
ldimm x.6000,r0
push r0		! int* arr2 (B[])
ldimm x.5000,r0
push r0		! int* arr1 (A[])

! sam poziv f-je
jsr xorAll

! ciscenje steka
pop r0
pop r0
pop r0

! smestanje A[0] na mem. lok. 9999h
ldmem x.5000,r0
stmem x.9999,r0

! inicijalizacija DMA1.4 kako bi se prekopirao nulti element niza A[]na 8h sukcesivnih lok. od lok. 5100h
ldimm x.8,r0
stmem x.F0C4,r0	! upisivanje u count reg DMA1.4
ldimm x.5000,r0
stmem x.F0C5,r0	! upisivanje u AR1(izvorisni registar) pocetnu adresu od A
ldimm x.5100,r0
stmem x.F0C6,r0	! upisivanje u AR2(odredisnji registar) pocetnu adresu(5100h)
ldimm x.0,r0
stmem x.F0C2,r0	! upisivanje ulaza 0 IVTP tabele u entry registar DMA1.4
ldimm x.0,r1	! semafor za proveru kraja DMA1.4
ldimm x.9E,r0 ! 1001 1110 -> 9E
stmem x.F0C0,r0

! cekanje DMA1.4 da zavrsi sa radom
ldimm x.1,r0
waitDMA14:and r2,r1,r0
beql waitDMA14
! DMA1.4 gotov sa radom
! startovanje periferije KP1.2 (prekid)
ldimm x.8,r1			! r1 <= ArrLengthA(KP1.2)
ldimm x.5000,r2			! r2 <= ArrOriginA(KP1.2)
ldimm x.0,r3			! r3 <= semafor

ldimm x.4,r0
stmem x.F142,r0			! KP1.2 <= entry <= 4h

ldimm x.E,r0	
stmem x.F140,r0			! KP1.2 <= START <= Fh

! startovanje DMA1.2 kontrolera
ldimm x.1,r0
stmem x.F044,r0	! upisivanje u count reg DMA1.2
ldimm x.9999,r0
stmem x.F045,r0	! upisivanje u AR1(izvorisni registar) adresu 9999h
ldimm x.1,r0
stmem x.F042,r0	! upisivanje ulaza 1 IVTP tabele u entry registar DMA1.2
ldimm x.0,r4	! semafor za proveru kraja DMA1.4
ldimm x.8E,r0 ! 1000 1110 -> 8E
stmem x.F040,r0

! cekanje DMA1.4 da zavrsi sa radom
ldimm x.1,r0
waitDMA12:and r4,r4,r0
beql waitDMA12

! provera je l KP1.2 zavrsio sa radom
ldimm x.0,r9
wait12: ldmem x.F140,r8
cmp r8,r9
bneq wait12
! kraj programa
halt 

! ----------------------------void xorAll(int* arr1, int* arr2, int n-----------------------------
xorAll:
push r0		! r0 <= bp(base pointer)
mvrpl r0,sp 	! r0 <= sp(current location)

! stavljanje registara koji se koriste u f-ji na stek
push r1
push r2
push r3
push r4
push r5

! dobijanje adresa nizova
ldrid [r0]x.2,r1	! r1 <= A[]
ldrid [r0]x.3,r2	! r2 <= B[]
ldrid [r0]x.4,r3	! r3 <= N


xorLoop:
! dobijanje vrednosti nizova
ldrid [r1]x.0,r4	! r4 <= A[i]
ldrid [r2]x.0,r5	! r5 <= B[i]

! xorovanje i provera cnt
xor r4,r4,r5
stri[r1],r4
inc r1
inc r2
dec r3
bneq xorLoop

! skidanje lokalnih promenjivih
pop r5
pop r4
pop r3
pop r2
pop r1
pop r0		! ciscenje base pointera
rts
! -----------------------------------------------------------------------------------------------
! -------------------------------------prekidna rutina KP1.1-------------------------------------
org x.500

! pushovanje registara koji se koriste u pr. rutini
push r0

! provera semafora (je l gasimo periferiju)
ldimm x.1,r0
and r5,r5,r0
bneq shutdown

! glavni deo pr. rut.
ldmem x.F103,r0
stri [r4],r0
inc r4
dec r3
beql semafor11
jmp end

! postavljanje semafora
semafor11:
ldimm x.1,r5
jmp end

! gasenje periferije
shutdown:
ldimm x.0,r0
stmem x.F100,r0
end:
pop r0
rti
! -----------------------------------------------------------------------------------------------
! -------------------------------------prekidna rutina DMA1.4------------------------------------
org x.3000
push r0
ldimm x.1,r1	! postavka semafora na 1
ldimm x.0,r0
stmem x.F0C0,r0	! iskljucivanje DMA1.4
pop r0
rti
! -----------------------------------------------------------------------------------------------
! -------------------------------------prekidna rutina KP1.2-------------------------------------
org x.1000

! pushovanje registara koji se koriste u pr. rutini
push r0

! provera semafora (je l gasimo periferiju)
ldimm x.1,r0
and r3,r3,r0
bneq shutdown12

! glavni deo pr. rut.
ldrid [r2]x.0,r0
stmem x.F143,r0
inc r2
dec r1
beql semafor12
jmp end12

! postavljanje semafora
semafor12:
ldimm x.1,r3
jmp end12

! gasenje periferije
shutdown12:
ldimm x.0,r0
stmem x.F140,r0
end12:
pop r0
rti
! -----------------------------------------------------------------------------------------------
! -------------------------------------prekidna rutina DMA1.2------------------------------------
org x.2500
push r0
ldimm x.1,r4	! postavka semafora na 1
ldimm x.0,r0
stmem x.F040,r0	! iskljucivanje DMA1.2
pop r0
rti