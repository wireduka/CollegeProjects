# Homework Assignment: Computer Architecture

## Section A (Variant A1)

### Main Program Location

The main program is located starting from address `4000h`.

### Interrupt Vector Table (IVTP Configuration)

The IV table and `ivtp` register must be initialized according to the following layout:

| Entry Number | Address | Device |
| ------------ | ------- | ------ |
| 5            | `0500h` | KP1.1  |
| 4            | `1000h` | KP1.2  |
| 2            | `1500h` | KP2.1  |
| 3            | `2000h` | DMA1.1 |
| 1            | `2500h` | DMA1.2 |
| 0            | `3000h` | DMA1.4 |

The IVTP register is configured to point to address `0100h`.

### Array Loading Task

Two arrays, **A** and **B**, each containing `8h` elements, must be loaded into memory:

* Array A → starting address `5000h`
* Array B → starting address `6000h`

The loading process must be performed concurrently using two different methods:

* Array A is loaded from peripheral **KP1.1** using the interrupt mechanism.
* Array B is loaded from peripheral **KP2.1** using polling by checking the ready status bit.

---

# Section B (Variant B1)

## XOR Subroutine

A subroutine:

```c
void xorArr(int* arr1, int* arr2, int n)
```

must be implemented.

The function performs a bitwise XOR operation between two arrays:

```c
arr1[i] = arr1[i] xor arr2[i]
```

where:

* `arr1` and `arr2` are pointers to the input arrays.
* `n` represents the number of elements in the arrays.

After both arrays have been loaded, the main program calls `xorArr` to calculate:

```
A[i] = A[i] XOR B[i]
```

The result of the operation is stored directly inside array A.

After returning from the subroutine:

* The first element of array A (`A[0]`) is stored at memory location:

```
9999h
```

---

## DMA1.4 Memory Copy Task

The first element of array A must additionally be copied to eight consecutive memory locations starting from:

```
5100h
```

This transfer is performed using the **DMA1.4 controller** configured in **burst mode**.

DMA1.4 performs a memory-to-memory transfer:

```
Source:
5000h

Destination:
5100h

Count:
8h elements
```

---

# Section C (Variant C2)

## KP1.2 Transmission

After completing Section B, the entire array A must be transmitted to peripheral **KP1.2**.

The transfer must use the interrupt mechanism.

---

## DMA1.2 Transfer Task

The value stored at memory location:

```
9999h
```

must be sent to the **DMA1.2 controller**.

DMA1.2 must operate in **burst mode**.

---

# Technical Notes

## Peripheral Control Registers

Standard peripherals use the following registers:

| Peripheral | Control | Status | Entry  | Data   |
| ---------- | ------- | ------ | ------ | ------ |
| KP1.1      | `F100`  | `F101` | `F102` | `F103` |
| KP1.2      | `F140`  | `F141` | `F142` | `F143` |
| KP2.1      | `F200`  | `F201` | `F202` | `F203` |

---

## Peripheral Control Register Format

```
15                                                        0
+----------------------------------------------------------+
| / / / / / / / / / / / / / | lvl | enb | i/o | strt |
+----------------------------------------------------------+
```

* `strt` — setting this bit to `1` starts the peripheral.
* `i/o`:

  * `1` → input device
  * `0` → output device
* `lvl`:

  * `1` → interrupt mode enabled
  * `0` → polling mode

---

## Peripheral Status Register

The lowest bit of the status register represents the ready flag.

```
15                                                        0
+----------------------------------------------------------+
| / / / / / / / / / / / / / / / / | rdy |
+----------------------------------------------------------+
```

---

# DMA Controllers

| Controller | Control | Entry  | Count  | AR1    | AR2    |
| ---------- | ------- | ------ | ------ | ------ | ------ |
| DMA1.2     | `F040`  | `F042` | `F044` | `F045` | `F046` |
| DMA1.4     | `F0C0`  | `F0C2` | `F0C4` | `F0C5` | `F0C6` |

---

## DMA Control Register

```
15                                                        0
+----------------------------------------------------------+
| / / / / / / / / | b | c | d | i | m | l | s | e | i/o |
+----------------------------------------------------------+
```

Where:

* `b` — burst mode enable
* `c` — cycle stealing mode
* `d` — decrement address
* `i` — increment address
* `m` — memory-to-memory transfer
* `l` — interrupt level
* `s` — start DMA
* `e` — enable DMA
* `i/o`:

  * `1` → peripheral-to-memory transfer
  * `0` → memory-to-peripheral transfer

---

# Implementation Notes

* The source file must begin with the mandatory hardware initialization block:

```
! inicijalizacija simulacije
...
! kraj inicijalizacije
```

* The initialization block must not be modified.
* The stack grows downward from higher memory addresses toward lower addresses.
* `SP` always points to the last pushed element.
* Function parameters are passed through the stack in reverse order.
* The caller is responsible for cleaning function arguments after returning.
* `int` values are 16-bit wide.
* Memory addresses are 16-bit wide.
* The smallest addressable memory unit is 16 bits.
