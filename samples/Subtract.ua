;
;  ========================================================================
;  uA Interpreter
;  ========================================================================
;  
;  This file is sample source code for the ua Interpreter.
;  
;  ua Interpreter is free software: you can redistribute it and/or
;  modify it under the terms of the GNU General Public License as published
;  by the Free Software Foundation, either version 3 of the License, or (at
;  your option) any later version.
;  
;  ua Interpreter is distributed in the hope that it will be useful,
;  but WITHOUT ANY WARRANTY; without even the implied warranty of 
;  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
;  General Public License for more details.
;
;  You should have received a copy of the GNU General Public License along
;  with ua Interpreter.  If not, see <http://www.gnu.org/licenses/>.
;  
;  (C) Copyright 2017, Gabor Kecskemeti (g.kecskemeti@ljmu.ac.uk)
;

; Substracts two 1 digit numbers as if they were scanned from the keyboard 
; Then prints the result on the top left corner of the screen 

; This sample shows features like:
; 1. Load numbers as text from memory (i.e., this could be the location a
;       where a keyboard scanner puts its results)
; 2. Convert single digit numbers from text to/form binary (e.g., ADC -48,A)
; 3. Print a single digit number to the top left corner (STC 0,A)

CONST in1 8
CONST in2 3 
entry:
LDC in1, A
; Memory contains the ASCII code of the number
; As if we have read it from the keyboard
ADC -48, A
LDC in2, B
ADC -48, B
; No subtraction operation!
; So we have to do a multiplication first
MLC -1,B
ADR B,A
ADC 48,A
; Printout to the display
STC 0,A
exit:
JMC exit